var DateFormats = {
	day: 'MM/DD/YYYY'
}

Handlebars.registerHelper('formatDate', function(formatKey, datetime) {
	if (datetime) {
		var f = DateFormats[formatKey];
		return moment.unix(datetime).format(f);
	} else {
		return '';
	}
});

