
var ShiftReportResidentListItem = Backbone.Model.extend({
    defaults: {
        id: null,
        residentId: null,
        createdByName: null,
        summary: null,
        notes: null,
    }
});

var ShiftReportResidents = Backbone.Collection.extend({
    model: ShiftReportResidentListItem,
});

function loadShiftReportsForResident(residentId) {
    var ret = null;
    $.ajax({
        url: '/api/shiftReports/' + residentId,
        async: false,
        success: function(data) {
            ret = new ShiftReportResidents(data); 
        }
    });
    return ret;
}

var ShiftReportResidentListItemView = Marionette.ItemView.extend({
    template: Handlebars.compile($('#shift-report-list-item-template').html()),
    tagName: 'li',
    className: 'list-group-item',
}); 

var ShiftReportResidentListView = Marionette.CollectionView.extend({
    childView: ShiftReportResidentListItemView,
    tagName: 'ul',
    className: 'list-group',
});
