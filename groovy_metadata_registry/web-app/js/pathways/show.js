
    //Enable embedded text bindings in html (http://mbest.github.io/knockout.punches/)
    ko.punches.interpolationMarkup.enable();

    //Create the main view model
    var vm = new AppViewModel();

    //Bind the view model to <body> and its descendants
    ko.applyBindings(vm, document.getElementById('content'));

    //Initial action on page load
    $('#CreatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });
