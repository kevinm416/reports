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

//$(document).ready(function() {
//	var trailingLink = this.location.href.replace(this.location.origin + '/', "")
//	console.log(trailingLink)
//    $('a[href="' + trailingLink + '"]').parent().addClass('active');
//});