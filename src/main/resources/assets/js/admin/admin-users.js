
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

var AdminUserListWithCreateView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#user-list-with-create-template').html()),
    initialize: function() {
        this.users = this.options.users;
        this.selectedUserModel = this.options.selectedUserModel;
    },
    regions: {
        'adminUserListRegion': '.user-list',
    },
    events: {
        'click .create-user-btn': 'createUser',
    },
    onShow: function() {
        this.adminUserListRegion.show(new AdminUserListView({
            collection: this.users,
            selectedUserModel: this.selectedUserModel,
        }));
    },
    createUser: function() {
        new Marionette.Region({el: '#modal-region'}).show(new CreateUserModalView({
            model: new User(),
            users: this.users,
        }));
    }
});

function parseBoolean(value) {
    return value === 'true';
}

var CreateUserModalView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#create-user-modal-template').html()),
    initialize: function() {
        this.users = this.options.users;
    },
    onShow: function() {
        this.$('.modal').modal('show');
    },
    events: {
        'change .name-input': 'changeName',
        'change .password-input': 'changePassword',
        'change .admin-yes': 'changeAdmin',
        'change .admin-no': 'changeAdmin',
        'click .save-user-button': 'createUser',
    },
    changeName: function(e) {
        var name = $(e.currentTarget).val();
        this.model.set('name', name);
    },
    changePassword: function(e) {
        var password = $(e.currentTarget).val();
        this.model.set('password', password);
    },
    changeAdmin: function(e) {
        var admin = parseBoolean($(e.currentTarget).val());
        this.model.set('admin', admin);
    },
    createUser: function() {
        this.model.save({}, {
            success: function(m, id, options) {
                var view = options.view;
                view.model.set('id', id);
                view.users.add(view.model);
                view.$('.modal').modal('hide');
            },
            error: function(m, r) {
                alert('error creating user\n' + _.escape(JSON.stringify(r)));
            },
            view: this,
        });
    }
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
        this.adminItemListRegion.show(new AdminUserListWithCreateView({
            users: this.users,
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
