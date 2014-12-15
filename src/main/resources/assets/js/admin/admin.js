
var AdminModel = Backbone.Model.extend({
    defaults: {
        state: 'users',
    },
});

var AdminTabModel = Backbone.Model.extend({
    defaults: {
        tabState: null,
        tabStateDisplayName: null,
    },
});

var AdminTabsModel = Backbone.Collection.extend({
    model: AdminTabModel,
});

var AdminTabView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#admin-tab-template').html()),
    tagName: 'li',
    initialize: function() {
        this.adminModel = this.options.adminModel;
        this.listenTo(this.adminModel, 'change:state', this.updateSelectedTab);
        this.updateSelectedTab();
    },
    events: {
        'click': 'selectCurrentTab',
    },
    selectCurrentTab: function() {
        this.adminModel.set('state', this.model.get('tabState'));
        this.updateSelectedTab();
    },
    updateSelectedTab: function() {
        this.$el.toggleClass('active', this.adminModel.get('state') == this.model.get('tabState'));
    }
});

var AdminTabsView = Marionette.CollectionView.extend({
    childView: AdminTabView,
    tagName: 'ul',
    className: 'nav nav-pills',
    childViewOptions: function() {
        return { adminModel: this.options.adminModel };
    }
});

var adminTabs = new AdminTabsModel([
    { tabState: 'users', tabStateDisplayName: 'Users' },
    { tabState: 'houses', tabStateDisplayName: 'Houses' },
    { tabState: 'residents', tabStateDisplayName: 'Residents'},
]);
var adminSelectedResidentModel = new SelectedResidentModel();
var adminSelectedHouseModel = new SelectedHouseModel();

var AdminView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-template').html()),
    initialize: function() {
        this.residents = this.options.residents;
        this.houses = this.options.houses;
    },
    regions: {
        'adminTabsRegion': '.admin-tabs',
        'adminMainRegion': '.admin-main',
    },
    modelEvents: {
        'change:state': 'changeTab',
    },
    onShow: function() {
        var adminTabsView = new AdminTabsView({
            collection: adminTabs,
            adminModel: this.model,
        });
        this.adminTabsRegion.show(adminTabsView);
        this.changeTab();
    },
    changeTab: function() {
        var that = this;
        if (this.model.get('state') == 'residents') {
            var residents = new ResidentsCollection();
            var houses = new HousesCollection();
            var selectedResidentModel = new SelectedResidentModel();
            $.when(residents.fetch(), houses.fetch()).then(function() {
                that.adminMainRegion.show(new AdminResidentsView({
                    residents: residents,
                    houses: houses,
                    selectedResidentModel: adminSelectedResidentModel,
                }));
            });
        } else if (this.model.get('state') == 'houses') {
            var houses = new HousesCollection();
            $.when(houses.fetch()).then(function() {
                that.adminMainRegion.show(new AdminHousesView({
                    houses: houses,
                    selectedHouseModel: adminSelectedHouseModel,
                }));
            });
        }
    }
});
