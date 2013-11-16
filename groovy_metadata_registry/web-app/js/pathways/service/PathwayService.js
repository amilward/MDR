define(['jquery', 'knockout'], function ($, ko) {
    var loadPathway = function (id) {
        //Load a pathway model from server
    };

    var savePathway = function (model) {

        var jsonModel = ko.toJSON(model);

        //Save pathway model to server
    };

    //Return an object literal (used like singleton/static functions)
    return {
        loadpathway: loadPathway,
        savePathway: savePathway
    };
});