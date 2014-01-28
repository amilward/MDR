'use strict';

/* Services */

var dataElementServices = angular.module('dataElementServices', ['ngResource']);

dataElementServices.factory('DataElementService', ['$resource',
    function($resource){
        return $resource('dataElement/:action/:id',
            {id: "@id", cmd: "@action"},
            {
            show: {method:'GET', params: {action: 'show'}},
            list: {method:'GET', params: {action: 'list'}, isArray: true},
            update: {method:'PUT', params: {action: 'update'}},
            save: {method: 'POST', params: {action: 'save'}}
            }
        );
    }]);


var relationshipTypeServices = angular.module('relationshipTypeServices', ['ngResource']);

relationshipTypeServices.factory('RelationshipTypeService', ['$resource',
    function($resource){
        return $resource('RelationshipType/:action/:id',
            {id: "@id", cmd: "@action"},
            {
                list: {method:'GET', params: {action: 'list'}, isArray: true}
            }
        );
    }]);


var catalogueServices = angular.module('catalogueServices', ['ngResource']);

catalogueServices.factory('CatalogueService', function() {
    return {
        statuses: [
                    {id: "DRAFT", title: 'DRAFT'},
                    {id: "PENDING", title: 'PENDING'},
                    {id: "FINALIZED", title: 'FINALIZED'},
                    {id: "REMOVED", title: 'REMOVED'}
                    ]
    }
});






