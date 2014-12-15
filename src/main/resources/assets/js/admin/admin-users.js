
var SelectedUserModel = Backbone.Model.extend({
    defaults: {
        userId: null,
    }
});

var AdminUserListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile("{{name}}"),
    tagName: 'li',
    className: 'list-group-item',
    initialize: function() {
        this.selectedUserModel = this.options.selectedUserModel;
        this.listenTo(this.selectedUserModel, 'change:userId', this.updateSelected);
    },
    events: {
        'click': 'onClick',
    },
    modelEvents: {
        'change': 'onChange',
    },
    onChange: function() {
        this.render();
    },
    onClick: function() {
        this.selectedUserModel.set('userId', this.model.id);
    },
    updateSelected: function() {
        this.$el.toggleClass('active', this.selectedUserModel.get('userId') == this.model.id);
    }
});

var AdminUserListView = Marionette.CollectionView.extend({
    childView: AdminUserListItemView,
    childViewOptions: function() {
        return { selectedUserModel: this.options.selectedUserModel };
    },
});

var AdminEditUserView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#admin-edit-user-template').html()),
    initialize: function() {
        this.selectedUserModel = this.options.selectedUserModel;
    },
    events: {
        'click .delete-user-button': 'deleteUser',
    },
    deleteUser: function() {
        this.model.destroy({
            success: function(m, r, options) {
                var view = options.view;
                view.selectedUserModel.set('userId', null);
                alert("User " +  _.escape(m.get('name')) + " deleted successfully")
            },
            error: function(m, r) {
                alert("Error deleting user\n" + _.escape(JSON.stringify(r)));
            },
            view: this,
        });
    }
});

var AdminUsersView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-main-template').html()),
    initialize: function() {
        this.users = this.options.users;
        this.selectedUserModel = this.options.selectedUserModel;
        this.listenTo(this.selectedUserModel, 'change:userId', this.updateSelectedUser);
    },
    regions: {
        'adminItemListRegion': '.admin-item-list',
        'adminItemDetailRegion': '.admin-item-details',
    },
    onShow: function() {
        this.adminItemListRegion.show(new AdminUserListView({
            collection: this.users,
            selectedUserModel: this.selectedUserModel,
        }));
        this.updateSelectedUser();
    },
    updateSelectedUser: function() {
        var selectedUser = this.users.get(this.selectedUserModel.get('userId'));
        this.adminItemDetailRegion.show(new AdminEditUserView({
            model: selectedUser,
            selectedUserModel: this.selectedUserModel,
        }));
    }
});
