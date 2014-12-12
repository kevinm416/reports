
var AdminModel = Backbone.Model.extend({
    defaults: {
        state: 'users',
        displayName: 'Users',
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

var adminTabs = new AdminTabsModel([
    { tabState: 'users', tabStateDisplayName: 'Users' },
    { tabState: 'houses', tabStateDisplayName: 'Houses' },
    { tabState: 'residents', tabStateDisplayName: 'Residents'},
]);

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

var AdminUsersView = Marionette.LayoutView.extend({
    template: Handlebars.compile("<h1>Admin View</h1>"),
    regions: {
        'adminUserListRegion': '.admin-user-list',
        'adminUserDetailsRegion': '.admin-user-details',
    }
});

var AdminView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-template').html()),
    regions: {
        'adminTabsRegion': '.admin-tabs',
        'adminMainRegion': '.admin-main',
    },
    onShow: function() {
        var adminModel = new AdminModel();
        var adminTabsView = new AdminTabsView({
            collection: adminTabs,
            adminModel: adminModel,
        });
        this.adminTabsRegion.show(adminTabsView);
        this.adminMainRegion.show(new AdminUsersView());
    }
});
