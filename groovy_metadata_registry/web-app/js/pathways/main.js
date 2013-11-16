//Make global library available to requirejs modules
define('jquery', function () { return jQuery; });
define('knockout', ko);
//define('d3', d3);

define(['AppViewModel'], function (AppViewModel) {

    //Create the main view model
    var vm = new AppViewModel();

    //Bind the view model to <body> and its descendants
    ko.applyBindings(vm, $('body')[0]);

    //Initial action on page load
    $('#CreatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });
});