
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

var ResidentCoordinator = Backbone.Model.extend({
    defaults: {
        id: null,
        name: null,
    }
});

var ResidentCoordinatorCollection = Backbone.Collection.extend({
    model: ResidentCoordinator,
    url: '/api/residentCoordinators'
});

var ResidentsCollection = Backbone.Collection.extend({
    model: Resident,
    url: '/api/residents',
});

var ResidentListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile("{{name}}"),
    tagName: 'li',
    className: 'list-group-item',
    initialize: function() {
        this.applicationModel = this.options.applicationModel;
        this.listenTo(this.applicationModel, 'change:residentId', this.updateSelected);
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
        this.$el.toggleClass('active', this.applicationModel.get('residentId') == this.model.id);
    }
});

var SelectedResidentTab = Backbone.Model.extend({
    defaults: {
        tabState: null,
        tabStateDisplayName: null,
    }
});

var SelectedResidentTabs = Backbone.Collection.extend({
    model: SelectedResidentTab
});

var selectedResidentTabs = new SelectedResidentTabs([
    { tabState: 'info', tabStateDisplayName: 'Info' },
    { tabState: 'care', tabStateDisplayName: 'Care' },
    { tabState: 'reports', tabStateDisplayName: 'Reports' },
]);

var SelectedResidentTabView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#selected-resident-tab-template').html()),
    tagName: 'li',
    initialize: function() {
        this.applicationModel = this.options.applicationModel;
        this.listenTo(this.applicationModel, 'change:residentPanelState', this.updateSelectedTab);
        this.updateSelectedTab();
    },
    events: {
        'click': 'selectCurrentTab',
    },
    selectCurrentTab: function() {
        this.applicationModel.set('residentPanelState', this.model.get('tabState'));
        this.updateSelectedTab();
    },
    updateSelectedTab: function() {
        this.$el.toggleClass('active', this.applicationModel.get('residentPanelState') == this.model.get('tabState'));
    },
});

var SelectedResidentTabsView = Marionette.CollectionView.extend({
   childView: SelectedResidentTabView,
   tagName: 'ul',
   className: 'nav nav-pills',
   childViewOptions: function() {
       return { applicationModel: this.options.applicationModel };
   }
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
        var birthdate = getUnixTimestampForDay(this.$('#birthdate-input').val()); 
        var houseId = this.$('#houseid-input').val();
        this.options.resident.set({
            'name': name,
            'birthdate': birthdate,
            'houseId': houseId,
        });
        this.options.resident.save();
    }
});

var ResidentListView = Marionette.CollectionView.extend({
    childView: ResidentListItemView,
    tagName: 'ul',
    className: 'list-group',
    childViewOptions: function() {
        return { applicationModel: this.options.applicationModel};
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
        residentId: null,
        residentPanelState: 'info',
    },
});

var ApplicationView = Marionette.LayoutView.extend({
    template: _.template($('#application-view-template').html()),
    regions: {
        'residentList': '#resident-list-region',
        'selectedResident': '.selected-resident-region',
        'selectedResidentTabs': '.selected-resident-tabs',
    },
    modelEvents: {
        'change:residentId': 'changeResident',
        'change:residentPanelState': 'changeResident',
    },
    onShow: function() {
        var residentListView = new ResidentListView({
            collection: this.options.residentsCollection,
            applicationModel: this.model
        })
        this.residentList.show(residentListView);
        this.createResidentTabs();
        this.changeResident();
    },
    createResidentTabs: function() {
        var tabsView = this.getSelectedResidentTabsView();
        this.selectedResidentTabs.show(tabsView);
    },
    changeResident: function() {
        var view = this.getSelectedResidentView();
        this.selectedResident.show(view);
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
        } else if (state == 'reports') {
            var residentId = this.model.get('residentId');
            var shiftReportResidents = new ShiftReportResidents(loadShiftReportsForResident(residentId, 7));
            return new ShiftReportResidentListViewWithFooter({
                collection: shiftReportResidents,
            });
        }
    },
    getSelectedResidentTabsView: function() {
        return selectedResidentTabsView = new SelectedResidentTabsView({
            collection: selectedResidentTabs,
            applicationModel: this.model,
        });
    },
    getSelectedHouse: function(selectedResident) {
        if (selectedResident) {
            return this.options.housesCollection.get(selectedResident.get('houseId'));
        } else {
            return null;
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
        'shiftReport': 'createShiftReportRoute',
        'shiftReport/:shiftReportId': 'viewShiftReportRoute',
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
        });
        this.residentsRoute();
    },
    createShiftReportRoute: function() {
        var houses = new HousesCollection();
        var residents = new ResidentsCollection();
        var residentCoordinators = new ResidentCoordinatorCollection();
        $.when(houses.fetch(), residents.fetch(), residentCoordinators.fetch())
        .then(function() {
            var shiftReportModel = new CreateShiftReportModel({
                residents: residents,
            });
            var shiftReportView = new CreateShiftReportView({
               model: shiftReportModel, 
               residents: residents,
               residentCoordinators: residentCoordinators,
               houses: houses,
            });
            app.appRegion.show(shiftReportView);    
        });
    },
    incidentReportRoute: function() {
        app.appRegion.show(new IncidentReportView());
    },
    viewShiftReportRoute: function(shiftReportId) {
        var shiftReportModel = loadShiftReportModel(shiftReportId);
        var shiftReportView = new ShiftReportView({
            model: shiftReportModel,
        });
        app.appRegion.show(shiftReportView);
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
    
$(function() {
    Backbone.history.start();
    app.start();
});
    
    
