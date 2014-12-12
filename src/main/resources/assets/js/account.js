
function loadCurrentUser() {
    var currentUser;
    $.ajax({
        url: '/api/users/whoAmI',
        async: false,
        success: function(data) {
            currentUser = new User(data);
        }
    });
    return currentUser;
}

var AccountModel = Backbone.Model.extend({
    defaults: {
        user: null,
        changingPassword: false,
        newPassword: null,
        newPasswordConfirm: null,
        passwordsMatch: false,
    },
});

var AccountView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#account-view-template').html()),
    serializeData: function() {
        ret = this.model.toJSON();
        ret.user = this.model.get('user').toJSON();
        return ret;
    },
    events: {
        'click .change-password-btn': 'changePassword',
        'click .confirm-change-password-btn': 'confirmChangePassword',
        'change .new-password-input': 'typePassword',
        'change .confirm-password-input': 'typePassword',
    },
    changePassword: function() {
        this.model.set('changingPassword', true);
    },
    typePassword: function() {
        var pw1 = this.$('.new-password-input').val();
        var pw2 = this.$('.confirm-password-input').val();
        this.model.set('newPassword', pw1);
        this.model.set('newPasswordConfirm', pw2);
        
        var passwordsMatch = pw1 && pw2 && pw1.length >= 8 && pw1 == pw2;
        
        this.model.set('passwordsMatch', passwordsMatch);
    },
    confirmChangePassword: function() {
        var newPassword = this.model.get('newPassword');
        $.ajax({
            url: '/api/users/changePassword',
            type: 'post',
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            context: this,
            data: JSON.stringify({
                password: newPassword,
            }),
            dataType: 'json',
            success: function() {
                var user = this.model.get('user');
                this.model = new AccountModel({
                    user: user,
                });  
            },
            error: function(r, textStatus, errorThrown) {
                alert("Failed to change the password. " +
                      "Status: " + textStatus + ". " +
                      "Error: " + errorThrown + ".");
            }
        });
    },
    
    modelEvents: {
        'change': 'onModelChange',
    },
    onModelChange: function() {
        this.render();
    },
});
