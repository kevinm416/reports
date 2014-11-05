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
        template: Handlebars.compile($('#resident-template').html()),
        tagName: 'li',
        className: 'list-group-item',
        initialize: function() {
            this.listenTo(this.options.applicationModel, 'change:residentId', this.updateSelected);
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
            this.options.applicationModel.set('residentId', this.model.id);
        },
        updateSelected: function() {
            this.$el.toggleClass('active', this.options.applicationModel.getResidentId() == this.model.id);
        }
    });
    
    var SelectedResidentView = Marionette.ItemView.extend({
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
    
    var ApplicationModel = Backbone.Model.extend({
        defaults: {
            residentId: null
        },
        getResidentId: function() {
            return this.get('residentId');
        }
    });
    
    var ApplicationView = Marionette.LayoutView.extend({
        template: _.template($('#application-view-template').html()),
        regions: {
            'residentList': '#resident-list-region',
            'selectedResident': '#selected-resident-region'
        },
        modelEvents: {
            'change:residentId': 'changeResident'
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
            var selectedResident = this.options.residentsCollection.get(this.model.get('residentId'));
            var selectedHouse = this.getSelectedHouse(selectedResident);
            selectedResidentView = new SelectedResidentView({
                resident: selectedResident,
                houses: this.options.housesCollection,
                selectedHouse: selectedHouse,
            });
            this.selectedResident.show(selectedResidentView);
        },
        getSelectedHouse: function(selectedResident) {
            if (selectedResident) {
                return this.options.housesCollection.get(selectedResident.get('houseId'));
            } else {
                return undefined;
            }
        }
    });
    
    var ShiftReportView = Marionette.ItemView.extend({
        template: _.template("<h1>Shift Report View!</h1>")
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
    console.log('created a new application model');
    
    app.addInitializer(function() {

        applicationModel.on('change', function() {
            console.log(this.toJSON())
        });
        
    });
    
    var AppRouter = Backbone.Router.extend({
        routes: {
            'residents': 'residentsRoute',
            'shiftReport': 'shiftReportRoute',
            'incidentReport': 'incidentReportRoute',
            '*all': 'residentsRoute'
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
        displayApplicationView: function(residents, houses) {
            app.appRegion.show(new ApplicationView({
                model: applicationModel,
                residentsCollection: residents,
                housesCollection: houses
            }));
        },
        shiftReportRoute: function() {
            app.appRegion.show(new ShiftReportView());
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
