$(function() {
    $("#login-submit").click(function(event) {
        event.preventDefault();
        $.post('/service/login', $('#login-form').serialize(), function(data) {
            window.location = '/';
        });
    });
    $("#logout-submit").click(function(event) {
        event.preventDefault();
        $.post('/service/logout', function(data) {
            window.location = '/';
        });
    });
});