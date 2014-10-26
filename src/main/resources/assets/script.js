(function($) {
	alert("working")
    var CreateResidentView = Backbone.View.extend({
        el: 'div.abPanel',
        
        events: {
        	'submit #createResidentForm' : 'sendAjax'
        },
        
        template: _.template($('#createResidentFormTemplate').html()),
        initialize: function() {
            _.bindAll(this, 'render');
        },
        render: function() {
            this.$el.html(this.template());
            return this;
        },
        sendAjax: function(e) {
        	e.preventDefault()
        	console.log(e);
        	alert("sending ajax");
        }
    })
    
    var CreateResidentModel = Backbone.Model.extend({
    })
    
    var createResidentModel = new CreateResidentModel();
    var createResidentView = new CreateResidentView({model: createResidentModel})
    createResidentView.render()
    
    var Resident = Backbone.Model.extend({
    	
    })
    
    var ResidentsCollection = Backbone.Collection.extend({
    	model: Resident,
    	url: '/api/residents'
    })
    
    var residents = new ResidentsCollection();
    residents.fetch();
    console.log(residents);
    
})(jQuery)