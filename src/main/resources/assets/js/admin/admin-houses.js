
var SelectedHouseModel = Backbone.Model.extend({
    defaults: {
        houseId: null,
    },
});

var HouseListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile("{{name}}"),
    tagName: 'li',
    className: 'list-group-item',
    initialize: function() {
        this.selectedHouseModel = this.options.selectedHouseModel;
        this.listenTo(this.selectedHouseModel, 'change:houseId', this.updateSelected);
    },
    events: {
        'click': 'onClick',
    },
    modelEvents: {
        'change': 'onChange',
    },
    onClick: function() {
        this.selectedHouseModel.set('houseId', this.model.id);
    },
    onChange: function(){
        this.render();
    },
    updateSelected: function() {
        this.$el.toggleClass('active', this.selectedHouseModel.get('houseId') == this.model.id)
    },
});

var HouseListView = Marionette.CollectionView.extend({
    childView: HouseListItemView,
    childViewOptions: function() {
        return { selectedHouseModel: this.options.selectedHouseModel};
    },
});

var CreateHouseModalView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#create-house-modal-template').html()),
    initialize: function() {
        this.houses = this.options.houses;
    },
    onShow: function() {
        this.$('.modal').modal('show');
    },
    events: {
        'click .save-house-button': 'createHouse',
    },
    createHouse: function() {
        var name = this.$('.name-input').val();
        this.model.set('name', name);
        this.model.save({}, {
            success: function(m, id, options) {
                var view = options.view;
                view.model.set('id', id);
                view.houses.add(view.model);
                view.$('.modal').modal('hide');
            },
            error: function(m, r) {
                view.$('.modal').modal('hide');
                alert('error creating resident\n' + JSON.stringify(r));
            },
            view: this,
        });
        console.log('save house: ' + name);
    }
});

var AdminHouseListWithCreateView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#house-list-with-create-template').html()),
    initialize: function() {
        this.houses = this.options.houses;
        this.selectedHouseModel = this.options.selectedHouseModel;
    },
    regions: {
        'adminHouseListRegion': '.house-list',
    },
    events: {
        'click .create-house-btn': 'createHouse',
    },
    onShow: function() {
        this.adminHouseListRegion.show(new HouseListView({
            collection: this.houses,
            selectedHouseModel: this.selectedHouseModel,
        }));
    },
    createHouse: function() {
        new Marionette.Region({el: '#modal-region'}).show(new CreateHouseModalView({
            model: new House(),
            houses: this.houses,
        }));
    }
});

var AdminHouseEditView = Marionette.ItemView.extend({
    template: Handlebars.compile('<h1>Edit House</h1>'),
})

var AdminHousesView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-main-template').html()),
    initialize: function() {
        this.houses = this.options.houses;
        this.selectedHouseModel = this.options.selectedHouseModel;
    },
    regions: {
        'adminItemListRegion': '.admin-item-list',
        'adminItemDetailsRegion': '.admin-item-details',
    },
    onShow: function() {
        this.adminItemListRegion.show(new AdminHouseListWithCreateView({
            houses: this.houses,
            selectedHouseModel: this.selectedHouseModel,
        }));
        this.adminItemDetailsRegion.show(new AdminHouseEditView({
            
        }));
    }
});
