
var ShiftReportResidentListItem = Backbone.Model.extend({
    defaults: {
        shiftReportMetadata: null,
        shiftReportResident: null,
    }
});

var ShiftReportResidents = Backbone.Collection.extend({
    model: ShiftReportResidentListItem,
});

function loadShiftReportsForResident(residentId, pageSize, lastShiftReportResidentId) {
    var ret = null;
    $.ajax({
        url: '/api/shiftReports/resident/' + residentId,
        data: {
            'pageSize': pageSize,
            'lastShiftReportResidentId': lastShiftReportResidentId,
        },
        async: false,
        success: function(data) {
            ret = data; 
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
    onShow: function() {
        this.$el.css({
            height: this.contentHeight,
            overflowY: 'scroll',
        });
    },
});

var ShiftReportResidentListViewWithFooter = Marionette.CompositeView.extend({
    template: Handlebars.compile($('#shift-report-list-with-footer-template').html()),
    childViewContainer: '#shift-report-list',
    childView: ShiftReportResidentListItemView,
    onShow: function() {
        var button = this.$el.find('#shift-report-list-loading-button'); 
        button.on('click', {outer: this}, function(e) {
            var outer = e.data.outer;
            button.button('loading');
            var loadedSize = outer.loadNextPage();
            if (loadedSize < 7) {
                button.hide();
            } else {
                button.button('reset');
            }
        });
    },
    loadNextPage: function() {
        var lastShiftReportResident = this.collection.last().get('shiftReportResident');
        var shiftReportResidentId = lastShiftReportResident.id;
        var residentId = lastShiftReportResident.residentId;
        var nextPage =loadShiftReportsForResident(residentId, 7, shiftReportResidentId);
        this.collection.add(nextPage);
        return nextPage.length;
    }
});
