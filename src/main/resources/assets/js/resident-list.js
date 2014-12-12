
var SelectedResidentModel = Backbone.Model.extend({
    defaults: {
        residentId: null,
    }
});

var ResidentListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile("{{name}}"),
    tagName: 'li',
    className: 'list-group-item',
    initialize: function() {
        this.selectedResidentModel = this.options.selectedResidentModel;
        this.listenTo(this.selectedResidentModel, 'change:residentId', this.updateSelected);
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
        this.selectedResidentModel.set('residentId', this.model.id);
    },
    updateSelected: function() {
        this.$el.toggleClass('active', this.selectedResidentModel.get('residentId') == this.model.id);
    },
});

var CreateResidentModalView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#create-resident-modal-template').html()),
    initialize: function() {
        this.residents = this.options.residents;
        this.houses = this.options.houses;
        this.applicationModel = this.options.applicationModel;
    },
    serializeData: function() {
        return {
            houses: this.houses.toJSON(),
        };
    },
    events: {
        'change .name-input': 'nameChanged',
        'change .birthdate-input': 'birthdateChanged',
        'change .houseid-input': 'houseChanged',
        'click .save-resident-button': 'createResident',
    },
    onShow: function() {
        this.$('.modal').modal('show');
        this.$('.birthdate-picker').datepicker();
    },
    nameChanged: function(e) {
        var name = $(e.currentTarget).val();
        this.model.set('name', name);
    },
    birthdateChanged: function(e) {
        var birthdate = getUnixTimestampForDay($(e.currentTarget).val());
        this.model.set('birthdate', birthdate);
    },
    houseChanged: function(e) {
        var houseId = $(e.currentTarget).val();
        this.model.set('houseId', houseId);
    },
    createResident: function(e) {
        this.model.save({}, {
            success: function(m, id, options) {
                var view = options.view;
                view.model.set('id', id);
                view.residents.add(view.model);
                view.$('.modal').modal('hide');
            },
            error: function(m, r) {
                alert('error creating resident\n' + JSON.stringify(r));
            },
            view: this,
        });
    },
});

var ResidentListView = Marionette.CollectionView.extend({
    childView: ResidentListItemView,
    childViewOptions: function() {
        return { selectedResidentModel: this.options.selectedResidentModel};
    },
});

var ResidentListViewWithFooter = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#resident-list-with-create-template').html()),
    initialize: function() {
        this.houses = this.options.houses;
        this.selectedResidentModel = this.options.selectedResidentModel;
    },
    regions: {
        'residentListRegion': '.resident-list-top-region',
        'createResidentRegion': '.resident-list-bottom-region',
    },
    events: {
        'click .create-resident-btn': 'createResident',
    },
    onShow: function() {
        this.residentListRegion.show(new ResidentListView({
            collection: this.collection,
            selectedResidentModel: this.selectedResidentModel,
        }));
    },
    createResident: function() {
        var createView = new CreateResidentModalView({
            model: new Resident(),
            residents: this.collection,
            houses: this.houses,
            applicationModel: this.applicationModel,
        });
        new Marionette.Region({el: '#modal-region'}).show(createView);
    }
});
