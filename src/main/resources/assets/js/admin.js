
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

var AdminUserEditView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-user-edit').html()),
    initialize: function() {
        this.resident = this.options.resident;
        this.houses = this.options.houses;
        this.selectedHouse = this.options.selectedHouse;
        this.selectedResidentModel = this.options.selectedResidentModel;
    },
    regions: {
        'userDetailsRegion': '.user-details',
    },
    events: {
        'click .save-resident-button': 'saveResident',
        'click .delete-resident-button': 'deleteResident',
    },
    serializeData: function() {
        return {
            residentId: this.selectedResidentModel.get('residentId'),
        }
    },
    onShow: function() {
        this.userDetailsRegion.show(new SelectedResidentInfoView({
            resident: this.resident,
            houses: this.houses,
            selectedHouse: this.selectedHouse,
            selectedResidentModel: this.selectedResidentModel,
        }));
    },
    saveResident: function(e) {
        e.preventDefault();
        var name = this.$('#name-input').val();
        var birthdate = getUnixTimestampForDay(this.$('#birthdate-input').val()); 
        var houseId = this.$('#houseid-input').val();
        this.resident.set({
            'name': name,
            'birthdate': birthdate,
            'houseId': houseId,
        });
        this.resident.save();
    },
    deleteResident: function() {
        this.resident.destroy({
            success: function(m, r, options) {
                var view = options.view;
                view.selectedResidentModel.set('residentId', null);
                alert("Resident " + m.get('name') + " deleted successfully");
            }, 
            error: function(m, r) {
                alert("Error deleting resident\n" + JSON.stringify(r));
            },
            view: this,
        });
    },
});

var AdminUsersView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-users').html()),
    initialize: function() {
        this.residents = this.options.residents;
        this.houses = this.options.houses;
        this.selectedResidentModel = this.options.selectedResidentModel;
        this.listenTo(this.selectedResidentModel, 'change:residentId', this.updateSelectedResident);
    },
    regions: {
        'adminUserListRegion': '.admin-user-list',
        'adminUserDetailsRegion': '.admin-user-details',
    },
    onShow: function() {
        this.adminUserListRegion.show(new ResidentListViewWithFooter({
            collection: this.residents,
            houses: this.houses,
            selectedResidentModel: this.selectedResidentModel,
        }));
        this.updateSelectedResident();
    },
    updateSelectedResident: function() {
        var selectedResident = this.residents.get(this.selectedResidentModel.get('residentId'));
        var selectedHouse = this.getSelectedHouse(selectedResident);
        this.adminUserDetailsRegion.show(new AdminUserEditView({
            resident: selectedResident,
            houses: this.houses,
            selectedHouse: selectedHouse,
            selectedResidentModel: this.selectedResidentModel,
        }));
    },
    getSelectedHouse: function(selectedResident) {
        if (selectedResident) {
            return this.houses.get(selectedResident.get('houseId'));
        } else {
            return null;
        }
    },
});

var adminTabs = new AdminTabsModel([
    { tabState: 'users', tabStateDisplayName: 'Users' },
    { tabState: 'houses', tabStateDisplayName: 'Houses' },
    { tabState: 'residents', tabStateDisplayName: 'Residents'},
]);
var adminSelectedResidentModel = new SelectedResidentModel();

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
                that.adminMainRegion.show(new AdminUsersView({
                    residents: residents,
                    houses: houses,
                    selectedResidentModel: adminSelectedResidentModel,
                }));
            });
        }
    }
});
