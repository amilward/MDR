$(function() {
    var currentDiv = '#dashboard-options';
    $('div.dashboard_option').mouseenter(function() {

        $(this).addClass("dashboard_option_highlighted").removeClass("dashboard_option_not_highlighted");
        $(this).find(".colour").fadeIn(200);
    })
        .mouseleave(function() {
            $(this).addClass("dashboard_option_not_highlighted").removeClass("dashboard_option_highlighted");
            $(this).find(".colour").fadeOut(200);
        }).click(function(){
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
