
var DateFormats = {
    day: 'MM/DD/YYYY',
    detail: 'MM/DD/YYYY h:mm a'
}

function getUnixTimestampForDay(s) {
    return moment(s, DateFormats.day).unix();
}

Handlebars.registerHelper('formatDate', function(formatKey, datetime) {
    if (datetime) {
        var f = DateFormats[formatKey];
        return moment.unix(datetime).format(f);
    } else {
        return '';
    }
});

