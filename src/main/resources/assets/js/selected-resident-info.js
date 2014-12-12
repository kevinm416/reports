
var SelectedResidentInfoView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#selected-resident-template').html()),
    initialize: function() {
        this.resident = this.options.resident;
        this.houses = this.options.houses;
        this.selectedHouse = this.options.selectedHouse;
        this.selectedResidentModel = this.options.selectedResidentModel;
    },
    serializeData: function() {
        if (this.resident && this.selectedHouse) {
            return {
                'resident': this.resident.toJSON(),
                'selectedHouse': this.selectedHouse.toJSON(),
                'houses': this.houses.toJSON()
            };
        } else {
            return {};
        }
    },
    onRender: function() {
        this.$el.find('#birthdate-picker').datepicker({
            pickTime: false
        });
    },
});

