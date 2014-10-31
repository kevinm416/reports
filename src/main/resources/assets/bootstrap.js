var DateFormats = {
	day: 'DD/MM/YYYY'
}

Handlebars.registerHelper('formatDate', function(formatKey, datetime) {
	if (datetime) {
		var f = DateFormats[formatKey];
		return moment(datetime).format(f);
	} else {
		return '';
	}
});