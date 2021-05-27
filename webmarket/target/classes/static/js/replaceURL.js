$(document).ready(function() {
    $("#locales").change(function () {

        var searchParams = new URLSearchParams(window.location.search);
        searchParams.set('lang',$('#locales').val())
        var newParams = searchParams.toString()
        window.location.search = newParams;
    });
});