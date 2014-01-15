$(function() {
    var currentDiv = '#dashboard-options';
    $('div.dashboard_option').click(function(){
        $(this).trigger('mouseleave');
        currentDiv = '#dashboard-' + $(this).attr('id');
        $('#dashboard-options').effect("slide", {direction: 'left', mode: 'hide'}, 500);
        $(currentDiv).effect("slide", {direction: 'right', mode: 'show'}, 500);
    });

    $('.dashboard-return').click(function(){
        $(currentDiv).effect("slide", {direction: 'right', mode: 'hide'}, 500);
        currentDiv = '#dashboard-options';
        $('#dashboard-options').effect("slide", {direction: 'left', mode: 'show'}, 500);
    });
});
