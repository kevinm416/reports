

var NavbarModel = Backbone.Model.extend({
    defaults: {
        admin: null,
    }
});

var NavbarView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#navbar-template').html()),
});
