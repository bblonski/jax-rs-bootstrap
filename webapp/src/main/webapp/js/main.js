$(function () {
    $('#login-form-nav, #login-form-full').find('input[type=submit]').click(function (event) {
        console.log("add click event");
        event.preventDefault();
        $.post('/service/login', $(this).closest('form').serialize(), function (data) {
            window.location = '/';
        });
    });
    $("#logout-submit").click(function (event) {
        event.preventDefault();
        $.post('/service/logout', function (data) {
            window.location = '/';
        });
    });
});