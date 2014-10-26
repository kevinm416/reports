(function($) {
	alert("working")
    var ShiftReportView = Backbone.View.extend({
        el: 'div.abPanel',
        
        events: {
        	'click #createShiftReportButton' : 'sendAjax'
        },
        
        template: _.template($('#createShiftReportTemplate').html()),
        initialize: function() {
            _.bindAll(this, 'createShiftReport');
        },
        createShiftReport: function() {
            this.$el.html(this.template());
        },
        sendAjax: function() {
        	alert("sending ajax");
        }
    })
    
    var ShiftReportModel = Backbone.Model.extend({
    })
    
    var shiftReportModel = new ShiftReportModel();
    var shiftReportView = new ShiftReportView({model: shiftReportModel})
    shiftReportView.createShiftReport()
})(jQuery)