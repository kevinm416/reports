(function($) {
    
	var ApplicationModel = Backbone.Model.extend({
		defaults: {
			residentId: null
		},
		getResidentId: function() {
			return this.get('residentId');
		}
	});
    
    var Resident = Backbone.Model.extend({
    	defaults: {
    		id: null,
    		name: null,
    		birthdate: null
    	}
    })
    
    var ResidentView = Marionette.ItemView.extend({
    	template: Handlebars.compile($('#resident-template').html()),
    	tagName: 'li',
    	className: 'list-group-item',
    	initialize: function() {
    		this.listenTo(this.options.applicationModel, 'change:residentId', this.updateSelected);
    	},
    	events: {
    		'click': 'onClick'
    	},
    	onClick: function() {
			this.options.applicationModel.set('residentId', this.model.id);
    	},
    	updateSelected: function() {
    		this.$el.toggleClass('active', this.options.applicationModel.getResidentId() == this.model.id);
    	}
    });
    
    var SelectedResidentView = Marionette.ItemView.extend({
    	template: Handlebars.compile($('#selected-resident-template').html()),
    });
    
    var ResidentsView = Marionette.CollectionView.extend({
    	childView: ResidentView,
    	tagName: 'ul',
    	className: 'list-group',
    	childViewOptions: function() {
    		return {'applicationModel': this.options.applicationModel};
    	}
    });
    
    var ResidentsCollection = Backbone.Collection.extend({
    	model: Resident,
    	url: '/api/residents'
    })
    
    var ApplicationView = Marionette.LayoutView.extend({
    	template: _.template($('#application-view-template').html()),
    	regions: {
    		'residentList': '#resident-list-region',
    		'selectedResident': '#selected-resident-region'
    	},
    	modelEvents: {
    		'change:residentId': 'changeResident'
    	},
    	onShow: function() {
    		residentsView = new ResidentsView({
    			collection: this.options.residentsCollection,
    			applicationModel: this.model
    		})
    		this.residentList.show(residentsView);
    		this.changeResident();
    	},
    	changeResident: function() {
    		selectedResidentView = new SelectedResidentView({
    			model: this.options.residentsCollection.get(this.model.get('residentId'))
    		});
    		this.selectedResident.show(selectedResidentView);
    	}
    });
    
    var app = new Marionette.Application();
    
    app.addRegions({
    	'app': '#app-region'
    });
    
    app.addInitializer(function() {
    	var residents = new ResidentsCollection();
    	residents.fetch();

    	var applicationModel = new ApplicationModel();
    	
    	applicationModel.on('change', function() {
    		console.log(this.toJSON())
    	});
    	
    	var appView = new ApplicationView({
    		model: applicationModel,
    		residentsCollection: residents
    	});
    	
    	this.app.show(appView);
    });
    
    $(function() {
    	app.start();	
    })
    
})(jQuery)