
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

Handlebars.registerHelper('formatBoolean', function(boolean) {
    if (boolean) {
        return 'Yes';
    } else {
        return 'No';
    }
});

function getUrlVars() {
    var hash = {};
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++) {
        arg = hashes[i].split('=');
        hash[arg[0]] = arg[1];
    }
    return hash;
}
