
var SelectedResidentInfoView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#selected-resident-template').html()),
    initialize: function() {
        this.resident = this.options.resident;
        this.houses = this.options.houses;
        this.selectedHouse = this.options.selectedHouse;
        this.applicationModel = this.options.applicationModel;
    },
    events: {
       'click .save-resident-button': 'saveResident',
       'click .delete-resident-button': 'deleteResident',
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
    saveResident: function(e) {
        e.preventDefault();
        var name = this.$('#name-input').val();
        var birthdate = getUnixTimestampForDay(this.$('#birthdate-input').val()); 
        var houseId = this.$('#houseid-input').val();
        this.resident.set({
            'name': name,
            'birthdate': birthdate,
            'houseId': houseId,
        });
        this.resident.save();
    },
    deleteResident: function() {
        this.resident.destroy({
            success: function(m, r, options) {
                var view = options.view;
                view.applicationModel.set('residentId', null);
                alert("Resident " + m.get('name') + " deleted successfully");
            }, 
            error: function(m, r) {
                alert("Error deleting resident\n" + JSON.stringify(r));
            },
            view: this,
        });
    },
});

