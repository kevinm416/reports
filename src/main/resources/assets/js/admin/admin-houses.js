
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
        this.adminItemListRegion.show(new HouseListView({
            collection: this.houses,
            selectedHouseModel: this.selectedHouseModel,
        }));
    }
});
