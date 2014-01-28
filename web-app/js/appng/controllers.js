'use strict';

/* Controllers */

var catalogueControllers = angular.module('catalogueControllers', []);

catalogueControllers.controller('HomeCtrl', function ($scope, $http, $log, security) {
    security.requestCurrentUser();
    $scope.login = function () {
        security.showLogin();
    }
    $scope.fetch = function () {
        $http.get("collectionBasket/dataElementsAsJSON")

            .success(function (data, status, headers, config) {
                $log.info(data);
                $log.info(status);
                $log.info(headers);
                $log.info(config);
                $scope.dataElements = data;
            })

            .error(function (data, status, headers, config) {
                $log.info(data);
                $log.info(status);
                $log.info(headers);
                $log.info(config);
            });
    };
})

// Dasboard controller, as for now it only exposes security service
catalogueControllers.controller('DashboardCtrl', ['$scope', '$http', 'security', function ($scope, $http, security) {
        $scope.security = security;
        $scope.fetch = function () {
            $http.get("collectionBasket/dataElementsAsJSON")
                .success(function (data, status, headers, config) {
                    $scope.dataElements = data;
                })
        };
    }]);


    // Data Element controller, as for now it only exposes params service
catalogueControllers.controller('DataElementList', ['$scope', '$http','ngTableParams', 'security', '$filter','DataElementService','CatalogueService', '$q', function ($scope, $http, ngTableParams, security, $filter, DataElementService, CatalogueService, $q) {
        $scope.security = security;
        $scope.tableParams

        $scope.data = DataElementService.list(function(data){
            $scope.tableParams = new ngTableParams({
                page: 1,            // show first page
                count: 10,          // count per page
                filter: {
                    // name: 'T'       // initial filter
                },
                sorting: {
                    name: 'asc'     // initial sorting
                }
            }, {
                total: data.length, // length of data
                getData: function ($defer, params) {
                    // use build-in angular filter
                    var filteredData = params.filter() ?
                        $filter('filter')(data, params.filter()) :
                        data;
                    var orderedData = params.sorting() ?
                        $filter('orderBy')(filteredData, params.orderBy()) :
                        data;

                    params.total(orderedData.length); // set total for recalc pagination
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });

        });


        $scope.status = function() {
            var def = $q.defer(),

            status = CatalogueService.statuses;

            def.resolve(status);
            return def;
        };



        $scope.selectedDataElements = [];

        $scope.clearSelection = function(){
            angular.forEach($scope.selectedDataElements, function(value, key){
                value.$selected = false;
            });
            $scope.selectedDataElements = [];

        }

        $scope.changeSelection = function(dataElement) {

            dataElement.$selected = !dataElement.$selected;

            if($scope.selectedDataElements.indexOf(dataElement) == -1){
                $scope.selectedDataElements.push(dataElement);
            }else{
                $scope.selectedDataElements.splice($scope.selectedDataElements.indexOf(dataElement), 1);
            }

        }


    }]);


// Show controller, as for now it only exposes params service
catalogueControllers.controller('DataElementShow', ['$scope', '$routeParams','ngTableParams', 'security', '$filter', 'DataElementService', '$location', 'CatalogueService', 'RelationshipTypeService', function ($scope, $routeParams, ngTableParams, security, $filter, DataElementService, $location, CatalogueService, RelationshipTypeService) {
    $scope.security = security;
    $scope.relationshipToAdd;
    $scope.tableParams;
    $scope.ngTableRelations;

    $scope.statuses = CatalogueService.statuses;

    $scope.dataElements = DataElementService.list();

    $scope.relationshipTypes = RelationshipTypeService.list(function(relationshipTypes){
        if(!relationshipTypes.errors){
            $scope.relationshipTypes = relationshipTypes;
        }else{
            var errors  = result.errors
            angular.forEach(errors, function(value, key){
                alert(value.message)
            });

        }
    });


    $scope.addRelationshipPanel = true;
    $scope.toggleAddRelationshipPanel = function() {
        $scope.addRelationshipPanel = $scope.addRelationshipPanel === false ? true: false;
    };

    $scope.addRelationship = function(relation){
        var newRelation = {};
        newRelation.id = relation.object.id;
        newRelation.name = relation.object.name;
        newRelation.type = relation.type.name;
        newRelation.relationshipDirection = relation.direction;
        newRelation.class = relation.object.class;
        $scope.dataElement.relations.push(newRelation);
        $scope.ngTableRelations.push(newRelation);
        $scope.relationshipToAdd = {};

        $scope.updateModel($scope.dataElement)

    }

    $scope.showDataElement = function(){ DataElementService.show({ id:  $routeParams.dataElementId}, function(dataElement){

        $scope.tableParams = new ngTableParams({
            page: 1,            // show first page
            count: 10,          // count per page
            filter: {
                // name: 'T'       // initial filter
            },
            sorting: {
                name: 'asc'     // initial sorting
            }
        }, {
            total: dataElement.relations.length, // length of data
            getData: function ($defer, params) {

                // use build-in angular filter
                var filteredData = params.filter() ?
                    $filter('filter')(dataElement.relations, params.filter()) :
                    dataElement.relations;
                var orderedData = params.sorting() ?
                    $filter('orderBy')(filteredData, params.orderBy()) :
                    dataElement.relations;

                params.total(orderedData.length); // set total for recalc pagination
                $scope.ngTableRelations = orderedData
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });

        $scope.dataElement =  dataElement

    });


    }

    $scope.showDataElement();

    $scope.updateModel = function(dataElement){

       DataElementService.update({id:  $scope.dataElement.id, version: $scope.dataElement.version, dataElement: $scope.dataElement},
       function(data){

           if(!data.errors){
               $scope.dataElement = data;
           }else{
               var errors  = data.errors
               angular.forEach(errors, function(value, key){
                   alert(value.message)
               });

               $scope.showDataElement();

           }

       });


    }

}]);



// Show controller, as for now it only exposes params service
catalogueControllers.controller('DataElementCreate', ['$scope', '$routeParams','ngTableParams', 'security', '$filter', 'DataElementService', 'RelationshipTypeService', '$location', 'CatalogueService', function ($scope, $routeParams, ngTableParams, security, $filter, DataElementService, RelationshipTypeService, $location, CatalogueService) {
    $scope.security = security;
    $scope.relationshipToAdd;
    $scope.tableParams;
    $scope.ngTableRelations = [];

    $scope.addRelationshipPanel = true;
    $scope.toggleAddRelationshipPanel = function() {
        $scope.addRelationshipPanel = $scope.addRelationshipPanel === false ? true: false;
    };

    $scope.relationshipTypes = RelationshipTypeService.list(function(relationshipTypes){
        if(!relationshipTypes.errors){
            $scope.relationshipTypes = relationshipTypes;
        }else{
            var errors  = result.errors
            angular.forEach(errors, function(value, key){
                alert(value.message)
            });

        }
    });

    $scope.statuses = CatalogueService.statuses;

    $scope.dataElement = {
        "name":"",
        "description":"",
        "definition":null,
        "versionNumber":"0",
        "status":"DRAFT",
        "relations":[
        ]
    };

    $scope.dataElements = DataElementService.list();


    $scope.createDataElement = function(){
        DataElementService.save({dataElement: $scope.dataElement}, function(result){
            if(!result.errors){
                //go to the show page for this element
                $location.path("/dataElement/show/" + result.id)
            }else{
                var errors  = result.errors
                angular.forEach(errors, function(value, key){
                    alert(value.message)
                });
            }
        });
    }

    $scope.addRelationship = function(relation){
        var newRelation = {};
        newRelation.id = relation.object.id;
        newRelation.name = relation.object.name;
        newRelation.type = relation.type.name;
        newRelation.direction = relation.direction;
        newRelation.className = relation.object.class;
        $scope.dataElement.relations.push(newRelation);
        $scope.ngTableRelations.push(newRelation);
        $scope.relationshipToAdd = {};

    }


    $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        count: 10,          // count per page
        filter: {
            // name: 'T'       // initial filter
        },
        sorting: {
            name: 'asc'     // initial sorting
        }
    }, {
        total:  $scope.ngTableRelations.length, // length of data
        getData: function ($defer, params) {

            // use build-in angular filter
            var filteredData = params.filter() ?
                $filter('filter')( $scope.ngTableRelations, params.filter()) :
                $scope.ngTableRelations;
            var orderedData = params.sorting() ?
                $filter('orderBy')(filteredData, params.orderBy()) :
                $scope.ngTableRelations;

            params.total(orderedData.length); // set total for recalc pagination
            $scope.ngTableRelations = orderedData;
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        }
    });

    }]);




// Dummy controller, as for now it only exposes params service
catalogueControllers.controller('DummyCtrl', ['$scope', '$routeParams', function ($scope, $routeParams) {
        $scope.params = $routeParams;

    }]);