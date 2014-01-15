//Pathway model
var DataElementModel = function () {
    var self = this;
    self.id = undefined;
    self.name = undefined;
    ko.track(self);


    self.previewCollections = function(){

        console.log('need to add DE preview here')

        window.location = '../../dataElement/show/' + self.id
    }
}