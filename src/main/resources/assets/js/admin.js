
var AdminView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#admin-template').html()),
});

var SelectedResidentTabsView = Marionette.CollectionView.extend({
    childView: SelectedResidentTabView,
    tagName: 'ul',
    className: 'nav nav-pills',
    childViewOptions: function() {
        return { applicationModel: this.options.applicationModel };
    }
});
