(function($) {
    
    var House = Backbone.Model.extend({
       defaults: {
           id: null,
           name: null
       }
    });
    
    var HousesCollection = Backbone.Collection.extend({
       model: House,
       url: '/api/houses'
    });
    
    var Resident = Backbone.Model.extend({
        defaults: {
            id: null,
            name: null,
            birthdate: null,
            houseId: null
        }
    });
    
    var ResidentsCollection = Backbone.Collection.extend({
        model: Resident,
        url: '/api/residents',
    });
    
    var ResidentView = Marionette.ItemView.extend({
        template: Handlebars.compile("{{name}}"),
        tagName: 'li',
        className: 'list-group-item',
        initialize: function() {
            this.applicationModel = this.options.applicationModel;
            this.listenTo(this.options.applicationModel, 'change:residentId', this.updateSelected);
            this.updateSelected();
        },
        events: {
            'click': 'onClick'
        },
        modelEvents: {
            'change': 'onChange'
        },
        onChange: function(e) {
            this.render();
        },
        onClick: function() {
            this.applicationModel.set('residentId', this.model.id);
        },
        updateSelected: function() {
            this.$el.toggleClass('active', this.applicationModel.getResidentId() == this.model.id);
        }
    });
    
    var SelectedResidentTabsModel = Backbone.Model.extend({
        defaults: {
            residentId: null,
        }
    });
    
    var SelectedResidentTabsView = Marionette.ItemView.extend({
       template: Handlebars.compile($('#selected-resident-tabs-template').html()),
    });
    
    var SelectedResidentCareView = Marionette.ItemView.extend({
       template: Handlebars.compile("<h1>Care View</h1>")
    });
    
    var SelectedResidentInfoView = Marionette.ItemView.extend({
        template: Handlebars.compile($('#selected-resident-template').html()),
        events: {
           'submit': 'onSubmit'
        },
        serializeData: function() {
            if (this.options.resident && this.options.selectedHouse) {
                return {
                    'resident': this.options.resident.toJSON(),
                    'selectedHouse': this.options.selectedHouse.toJSON(),
                    'houses': this.options.houses.toJSON()
                }
            } else {
                return {};
            }
        },
        onRender: function() {
            this.$el.find('#birthdate-picker').datepicker({
                pickTime: false
            });
        },
        onSubmit: function(e) {
            e.preventDefault();
            var name = this.$('#name-input').val();
            var birthdate = moment(this.$('#birthdate-input').val(), "MM/DD/YYYY").unix();
            var houseId = this.$('#houseid-input').val();
            this.options.resident.set({
                'name': name,
                'birthdate': birthdate,
                'houseId': houseId,
            });
            this.options.resident.save();
        }
    });
    
    var ResidentsView = Marionette.CollectionView.extend({
        childView: ResidentView,
        tagName: 'ul',
        className: 'list-group',
        childViewOptions: function() {
            return {'applicationModel': this.options.applicationModel};
        }
    });
    
    var ResidentsCollection = Backbone.Collection.extend({
        model: Resident,
        url: '/api/residents',
        events: {
           'all': 'test'
        },
        test: function(e) {
           console.log('change in residents collection:', e);
        }
    });
    
    var residentPanelStates = [
        'info',
        'care',
        'medication',
    ];
    
    var ApplicationModel = Backbone.Model.extend({
        defaults: {
            residentId: null,
            residentPanelState: 'info',
        },
        getResidentId: function() {
            return this.get('residentId');
        }
    });
    
    var ApplicationView = Marionette.LayoutView.extend({
        template: _.template($('#application-view-template').html()),
        regions: {
            'residentList': '#resident-list-region',
            'selectedResident': '#selected-resident-region',
            'selectedResidentTabs': '#selected-resident-tabs',
        },
        modelEvents: {
            'change:residentId': 'changeResident',
            'change:residentPanelState': 'changeResident',
        },
        onShow: function() {
            var residentsView = new ResidentsView({
                collection: this.options.residentsCollection,
                applicationModel: this.model
            })
            this.residentList.show(residentsView);
            this.changeResident();
        },
        changeResident: function() {
            var view = this.getSelectedResidentView();
            this.selectedResident.show(view);
            
            var tabsView = this.getSelectedResidentTabsView();
            this.selectedResidentTabs.show(tabsView);
        },
        getSelectedResidentView: function() {
            var selectedResident = this.options.residentsCollection.get(this.model.get('residentId'));
            var selectedHouse = this.getSelectedHouse(selectedResident);
            var state = this.model.get('residentPanelState');
            if (state == 'info') {
                return new SelectedResidentInfoView({
                    resident: selectedResident,
                    houses: this.options.housesCollection,
                    selectedHouse: selectedHouse,
                });
            } else if (state == 'care') {
                return new SelectedResidentCareView({});
            }
        },
        getSelectedResidentTabsView: function() {
            var selectedResidentTabsModel = new SelectedResidentTabsModel({
                'residentId': this.model.get('residentId'),
            });
            return selectedResidentTabsView = new SelectedResidentTabsView({
                model: selectedResidentTabsModel,
            });
        },
        getSelectedHouse: function(selectedResident) {
            if (selectedResident) {
                return this.options.housesCollection.get(selectedResident.get('houseId'));
            } else {
                return undefined;
            }
        },
    });
    
    var ShiftReportResidentView = Marionette.ItemView.extend({
        template: Handlebars.compile($('#shift-report-resident-template').html()),
    });
    
    var ShiftReportResidentsView = Marionette.CollectionView.extend({
        childView: ShiftReportResidentView,
    });
    
    var ShiftReportModel = Backbone.Model.extend({
       defaults: {
           houses: null,
           residents: null,
       },
    });
    
    var ShiftReportView = Marionette.LayoutView.extend({
        template: Handlebars.compile($('#create-shift-report-template').html()),
        regions: {
            'formTop': '#create-shift-report-top-template',
            'formBottom': '#create-shift-report-bottom-template',
        },
        onShow: function() {
            var shiftReportTopView = new ShiftReportTopView({
                model: this.model,
            });
            this.formTop.show(shiftReportTopView);
            
            var shiftReportBottomView = new ShiftReportResidentsView({
                collection: this.model.get('residents'),
            });
            this.formBottom.show(shiftReportBottomView);
        }
    });
    
    var ShiftReportTopView = Marionette.ItemView.extend({
        template: Handlebars.compile($('#create-shift-report-top-template').html()),
        serializeData: function() {
            return {
                'houses': this.model.get('houses').toJSON(),
            }
        },
    });
    
    var IncidentReportView = Marionette.ItemView.extend({
        template: _.template("<h1>Incident Report View!</h1>")
    });
    
    var app = new Marionette.Application({
        regions: {
            'appRegion': '#app-region'
        }
    });
    
    var applicationModel = new ApplicationModel();
    
    var AppRouter = Backbone.Router.extend({
        routes: {
            'residents': 'residentsRoute',
            'residents/:id/:residentPanelState': 'residentsPanelRoute',
            'shiftReport': 'shiftReportRoute',
            'incidentReport': 'incidentReportRoute',
            '*all': 'defaultRoute'
        },
        defaultRoute: function() {
            this.navigate('residents', {trigger:true});
        },
        residentsRoute: function() {
            var residents = new ResidentsCollection();
            var houses = new HousesCollection();
            $.when(residents.fetch(), houses.fetch()).then(function() {
                app.appRegion.show(new ApplicationView({
                    model: applicationModel,
                    residentsCollection: residents,
                    housesCollection: houses
                }));
            });
        },
        residentsPanelRoute: function(residentId, residentPanelState) {
            applicationModel.set({
                'residentId': residentId,
                'residentPanelState': residentPanelState,
            })
        },
        shiftReportRoute: function() {
            var houses = new HousesCollection();
            var residents = new ResidentsCollection();
            $.when(houses.fetch(), residents.fetch()).then(function() {
                var shiftReportModel = new ShiftReportModel({
                    houses: houses,
                    residents: residents,
                });
                var shiftReportView = new ShiftReportView({
                   model: shiftReportModel, 
                });
                app.appRegion.show(shiftReportView);    
            });
        },
        incidentReportRoute: function() {
            app.appRegion.show(new IncidentReportView());
        },
    });
    
    var appRouter = new AppRouter();
    appRouter.on('route', function() {
        var navbarItems = $('ul.nav li');
        navbarItems.removeClass('active');
        var location = document.location;
        var trailingLink = location.href.replace(location.origin + '/', "")
        var trailingLinkItems = navbarItems.find('a[href="' + trailingLink + '"]')
        if (trailingLinkItems.length > 0) {
            trailingLinkItems.parent().addClass('active')
        } else {
            navbarItems.first().addClass('active');
        }
    });
    
    Backbone.history.start();
    
    $(function() {
        app.start();
    })
    
})(jQuery)
