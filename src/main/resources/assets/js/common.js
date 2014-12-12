
var DateFormats = {
    day: 'MM/DD/YYYY',
    detail: 'MM/DD/YYYY h:mm a'
};

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

// This collapses the mobile navbar dropdown after the user clicks on an option
$('.nav a').click(function(){
    var toggle = $('.navbar-toggle');
    if (toggle.is(':visible')) {
        toggle.click();
    }
});
