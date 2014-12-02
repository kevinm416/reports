
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
    },
});

var CreateResidentModalView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#create-resident-modal-template').html()),
    initialize: function() {
        this.residents = this.options.residents;
        this.applicationModel = this.options.applicationModel;
    },
    onShow: function() {
        this.$('.modal').modal('show');
    },
});

var ResidentListViewWithFooter = Marionette.CompositeView.extend({
    template: Handlebars.compile($('#resident-list-template').html()),
    childViewContainer: '.resident-list-top-region',
    childView: ResidentListItemView,
    initialize: function() {
        this.residents = this.options.residents;
        this.applicationModel = this.options.applicationModel;
    },
    childViewOptions: function() {
        return { applicationModel: this.options.applicationModel};
    },
    events: {
        'click .create-resident-btn': 'createResident',
    },
    createResident: function() {
        var createView = new CreateResidentModalView({
            model: new Resident(),
            residents: this.residents,
            applicationModel: this.applicationModel,
        });
        new Marionette.Region({el: '#modal-region'}).show(createView);
    }
});
