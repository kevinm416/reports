
function loadShiftReportModel(shiftReportId) {
    var shiftReportModel;
    $.ajax({
        url: '/api/shiftReports/' + shiftReportId,
        async: false,
        success: function(data) {
            shiftReportModel = new ShiftReportModel(data);
        }
    });
    return shiftReportModel;
}

var ShiftReportModel = Backbone.Model.extend({
    defaults: {
        id: null,
        houseName: null,
        date: null,
        createdByName: null,
        shift: null,
        timeCreated: null,
        keysAccountedFor: null,
        keysAccountedForReason: null,
        shiftReportResidents: null,
    },
});

var ShiftReportView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#shift-report-template').html()),
});

