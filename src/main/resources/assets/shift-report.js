
var ShiftReportResidentListItem = Backbone.Model.extend({
    defaults: {
        residentId: null,
        residentName: null,
        notes: null,
    }
});

var ShiftReportResidentsList = Backbone.Collection.extend({
    model: ShiftReportResidentListItem
});

var ShiftReportResidentListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#shift-report-resident-template').html()),
});

var ShiftReportResidentsListView = Marionette.CollectionView.extend({
    childView: ShiftReportResidentListItemView,
});

var ShiftReportModel = Backbone.Model.extend({
   defaults: {
       houses: null,
       residents: null,
       selectedHouseId: null,
   },
});

function convertResidentsToShiftReportResidentListItems(residents) {
    return $.map(residents, function(resident) {
        return new ShiftReportResidentListItem({
            residentId: resident.id,
            residentName: resident.get('name'),
        });
    });
}

var ShiftReportView = Marionette.LayoutView.extend({
    template: Handlebars.compile($('#create-shift-report-template').html()),
    regions: {
        'formTop': '#create-shift-report-top-template',
        'formBottom': '#create-shift-report-bottom-template',
    },
    modelEvents: {
        'change:selectedHouseId': 'populateResidents',
    },
    onShow: function() {
        var shiftReportTopView = new ShiftReportTopView({
            model: this.model,
        });
        this.formTop.show(shiftReportTopView);
    },
    populateResidents: function() {
        var houseId = this.model.get('selectedHouseId');
        var filteredResidents = this.model.get('residents').where({houseId: houseId});
        var shiftReportResidents = convertResidentsToShiftReportResidentListItems(filteredResidents);
        var shiftReportBottomView = new ShiftReportResidentsListView({
            collection: new ShiftReportResidentsList(shiftReportResidents),
        });
        this.formBottom.show(shiftReportBottomView);
    },
});

var ShiftReportTopView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#create-shift-report-top-template').html()),
    serializeData: function() {
        return {
            'houses': this.model.get('houses').toJSON(),
        }
    },
    events: {
        'change #houseid-input': 'selectHouse'
    },
    selectHouse: function() {
        var selectedHouseId = this.$('#houseid-input').val();
        this.model.set('selectedHouseId', selectedHouseId);
    },
});