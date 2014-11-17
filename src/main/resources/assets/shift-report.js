
var ShiftReportSubmitView = Marionette.ItemView.extend({
    template: Handlebars.compile(
        "<div class='form-group'>" +
            "<button type='submit' class='btn btn-default col-sm-1 col-sm-offset-8'>Submit</button>" +
        "</div>"),
});

var ShiftReportResidentListItem = Backbone.Model.extend({
    defaults: {
        residentId: null,
        residentName: null,
        summary: null,
        notes: null,
    }
});

var ShiftReportResidentsList = Backbone.Collection.extend({
    model: ShiftReportResidentListItem
});

var ShiftReportResidentListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#shift-report-resident-template').html()),
    events: {
        'change #summary': 'changeSummary',
        'change #notes': 'changeReport',
    },
    changeSummary: function() {
        var summary = $(e.currentTarget).val(); 
        this.model.set('summary', summary);
    },
    changeReport: function() {
        var notes = $(e.currentTarget).val();
        this.model.set('notes', notes);
    },
});

var ShiftReportResidentsListView = Marionette.CollectionView.extend({
    childView: ShiftReportResidentListItemView,
});

var ShiftReportModel = Backbone.Model.extend({
   defaults: {
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
    initialize: function(){
        this.houses = this.options.houses;
        this.shiftReportResidents = null;
        this.topModel = new ShiftReportTopModel({
            date: this.getToday(),
        });
        this.listenTo(this.topModel, 'change:houseId', this.populateResidents);
    },
    regions: {
        'formTop': '#create-shift-report-top',
        'formBottom': '#create-shift-report-bottom',
        'submitRegion': '#create-shift-report-submit',
    },
    events: {
        'submit': 'onSubmit',
    },
    modelEvents: {
        'change:selectedHouseId': 'populateResidents',
    },
    onShow: function() {
        var shiftReportTopView = new ShiftReportTopView({
            houses: this.houses,
            model: this.topModel,
            today: this.getToday(),
        });
        this.formTop.show(shiftReportTopView);
    },
    populateResidents: function(e) {
        var houseId = this.topModel.get('houseId');
        var filteredResidents = this.model.get('residents').where({houseId: houseId});
        var shiftReportResidents = convertResidentsToShiftReportResidentListItems(filteredResidents);
        var shiftReportBottomView = new ShiftReportResidentsListView({
            collection: new ShiftReportResidentsList(shiftReportResidents),
        });
        this.formBottom.show(shiftReportBottomView);
        this.submitRegion.show(new ShiftReportSubmitView());
    },
    onSubmit: function(e) {
        e.preventDefault();
        console.log(e);
    },
    getToday: function() {
        return moment().format(DateFormats.day);
    }
});

var ShiftReportTopModel = Backbone.Model.extend({
    defaults: {
        date: null,
        shift: null,
        keysAccountedFor: null,
        keysAccountedForReason: null,
        houseId: null,
    }
});

var ShiftReportTopView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#create-shift-report-top-template').html()),
    initialize: function() {
        this.houses = this.options.houses;
        this.today = this.options.today;
    },
    serializeData: function() {
        return {
            'houses': this.houses.toJSON(),
            'today': this.today,
        }
    },
    events: {
        'change #date-input': 'changeDate',
        'change #shift-input': 'changeShift',
        'change #houseid-input': 'changeHouse',
        'change #keys-accounted-for-yes': 'changeKeys',
        'change #keys-accounted-for-no': 'changeKeys',
    },
    changeDate: function(e) {
        var date = getUnixTimestampForDay($(e.currentTarget).val());
        this.model.set('date', date);
    },
    changeShift: function(e) {
        var shift = $(e.currentTarget).val();
        this.model.set('shift', shift);
    },
    changeHouse: function(e) {
        var selectedHouseId = $(e.currentTarget).val();
        this.model.set('houseId', selectedHouseId);
    },
    changeKeys: function(e) {
        if (this.$('#keys-accounted-for-yes').is(':checked')) {
            this.model.set('keysAccountedFor', true);
        } else if (this.$('#keys-accounted-for-no').is(':checked')) {
            this.model.set('keysAccountedFor', false);
            this.model.set('keysAccountedForReason', this.$('#keys-accounted-for-reason').val());
        } else {
            throw "Neither yes nor no selected for keys accounted for";
        }
    },
    onShow: function(){
        this.$el.find('#date-picker').datepicker({
            pickTime: false
        });
    },
});