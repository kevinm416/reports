
var AdminResidentEditView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-resident-edit').html()),
    initialize: function() {
        this.resident = this.options.resident;
        this.houses = this.options.houses;
        this.selectedHouse = this.options.selectedHouse;
        this.selectedResidentModel = this.options.selectedResidentModel;
    },
    regions: {
        'userDetailsRegion': '.user-details',
    },
    events: {
        'click .save-resident-button': 'saveResident',
        'click .delete-resident-button': 'deleteResident',
    },
    serializeData: function() {
        return {
            residentId: this.selectedResidentModel.get('residentId'),
        }
    },
    onShow: function() {
        this.userDetailsRegion.show(new SelectedResidentInfoView({
            resident: this.resident,
            houses: this.houses,
            selectedHouse: this.selectedHouse,
            selectedResidentModel: this.selectedResidentModel,
        }));
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
                view.selectedResidentModel.set('residentId', null);
                alert("Resident " + _.escape(m.get('name')) + " deleted successfully");
            }, 
            error: function(m, r) {
                alert("Error deleting resident\n" + _.escape(JSON.stringify(r)));
            },
            view: this,
        });
    },
});

var AdminResidentsView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#admin-main-template').html()),
    initialize: function() {
        this.residents = this.options.residents;
        this.houses = this.options.houses;
        this.selectedResidentModel = this.options.selectedResidentModel;
        this.listenTo(this.selectedResidentModel, 'change:residentId', this.updateSelectedResident);
    },
    regions: {
        'adminItemListRegion': '.admin-item-list',
        'adminItemDetailsRegion': '.admin-item-details',
    },
    onShow: function() {
        this.adminItemListRegion.show(new ResidentListViewWithFooter({
            collection: this.residents,
            houses: this.houses,
            selectedResidentModel: this.selectedResidentModel,
        }));
        this.updateSelectedResident();
    },
    updateSelectedResident: function() {
        var selectedResident = this.residents.get(this.selectedResidentModel.get('residentId'));
        var selectedHouse = this.getSelectedHouse(selectedResident);
        this.adminItemDetailsRegion.show(new AdminResidentEditView({
            resident: selectedResident,
            houses: this.houses,
            selectedHouse: selectedHouse,
            selectedResidentModel: this.selectedResidentModel,
        }));
    },
    getSelectedHouse: function(selectedResident) {
        if (selectedResident) {
            return this.houses.get(selectedResident.get('houseId'));
        } else {
            return null;
        }
    },
});
