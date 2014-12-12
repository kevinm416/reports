
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
    },
    urlRoot: '/api/residents',
});

var User = Backbone.Model.extend({
    defaults: {
        id: null,
        name: null,
    }
});

var UserCollection = Backbone.Collection.extend({
    model: User,
    url: '/api/users'
});

var ResidentsCollection = Backbone.Collection.extend({
    model: Resident,
    url: '/api/residents',
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
    onShow: function() {
        this.updateSelectedTab();
    },
    selectCurrentTab: function() {
        this.applicationModel.set('residentPanelState', this.model.get('tabState'));
        this.updateSelectedTab();
    },
    updateSelectedTab: function() {
        this.$el.toggleClass('active', this.applicationModel.get('residentPanelState') === this.model.get('tabState'));
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
    initialize: function() {
        this.houses = this.options.houses;
        this.residents = this.options.residents;
    },
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
        var residentListView = new ResidentListViewWithFooter({
            collection: this.residents,
            applicationModel: this.model,
            houses: this.houses,
        });
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
        var selectedResident = this.residents.get(this.model.get('residentId'));
        var selectedHouse = this.getSelectedHouse(selectedResident);
        var state = this.model.get('residentPanelState');
        if (state == 'info') {
            return new SelectedResidentInfoView({
                applicationModel: this.model,
                houses: this.houses,
                resident: selectedResident,
                selectedHouse: selectedHouse,
            });
        } else if (state == 'reports') {
            var residentId = this.model.get('residentId');
            var shiftReportResidents = new ShiftReportResidents(loadShiftReportsForResident(residentId, 7));
            return new ShiftReportResidentListViewWithFooter({
                collection: shiftReportResidents,
            });
        }
    },
    getSelectedResidentTabsView: function() {
        return new SelectedResidentTabsView({
            collection: selectedResidentTabs,
            applicationModel: this.model,
        });
    },
    getSelectedHouse: function(selectedResident) {
        if (selectedResident) {
            return this.houses.get(selectedResident.get('houseId'));
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
        'admin': 'adminRoute',
        'account': 'accountRoute',
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
                residents: residents,
                houses: houses,
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
        var users = new UserCollection();
        $.when(houses.fetch(), residents.fetch(), users.fetch())
        .then(function() {
            var shiftReportModel = new CreateShiftReportModel({
                residents: residents,
            });
            var createShiftReportView = new CreateShiftReportView({
               model: shiftReportModel, 
               residents: residents,
               residentCoordinators: users,
               houses: houses,
            });
            app.appRegion.show(createShiftReportView);    
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
    adminRoute: function() {
        app.appRegion.show(new AdminView());
    },
    accountRoute: function() {
        var user = loadCurrentUser();
        var model = new AccountModel({
            user: user,
        });
        app.appRegion.show(new AccountView({
            model: model,
        }));
    },
});

var appRouter = new AppRouter();
appRouter.on('route', function() {
    var navbarItems = $('ul.nav li');
    navbarItems.removeClass('active');
    var location = document.location;
    var trailingLink = location.href.replace(location.origin + '/', "");
    var trailingLinkItems = navbarItems.find('a[href="' + trailingLink + '"]');
    if (trailingLinkItems.length > 0) {
        trailingLinkItems.parent().addClass('active');
    } else {
        navbarItems.first().addClass('active');
    }
});
    
$(function() {
    Backbone.history.start();
    app.start();
});
    
    
