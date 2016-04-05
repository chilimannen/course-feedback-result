/**
 * @author Robin Duda
 */

var application = {
    handlers: {},
    id: "",
    preset: false,

    subscribe: function (event, callback) {
        if (this.handlers[event] == null)
            this.handlers[event] = [];

        this.handlers[event].push(callback);
    },

    publish: function (event, data) {
        for (var subscriber = 0; subscriber < this.handlers[event].length; subscriber++)
            this.handlers[event][subscriber](data);
    }
};

application.subscribe('vote-select', function (event) {
    application.id = event.id;
    $('#user-panel').show();
});

application.subscribe('close-event', function () {
    setTimeout(function () {
        $('#user-panel').hide();
    }, 3500);
});

$(document).ready(function () {
    if ($.cookie("vote.id") != "") {
        application.preset = true;
        application.id = $.cookie("vote.id");
    } else
        $('#user-panel').hide();
});