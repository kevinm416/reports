(function($) {
	alert("working")
    var a = Backbone.View.extend({
        el: 'div.abPanel',
        template: _.template($('#createShiftReportTemplate').html()),
        initialize: function() {
            _.bindAll(this, 'createShiftReport');
        },
        createShiftReport: function() {
            this.$el.html(this.template());
        }
    })
    
    var b = Backbone.Model.extend({
    	sync: function(method, model, options) {
    		if (method === 'create' || method === 'update') {
    			return $.ajax({
    				dataType: 'json',
    				url: '../slkfjlksdjfls;dkfj',
    				data: {
    					house: this.get('house'),
    					shift: this.get('shift')
    				},
    				success: function(data) {
    					alert("success: " + data)
    				}
    			})
    		}
    	}
    })
    
})(jQuery)