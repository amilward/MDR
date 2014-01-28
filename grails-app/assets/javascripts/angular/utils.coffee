angular.module('utils', ['ngResource'])

#
# The Grails resource, used for all interaction with the Grails environment.
#
.service 'Grails', ($resource) ->
        getResource: (scope) ->
            $resource "/:grailsAppName/:controller/:action/:id",
              {grailsAppName: scope.grailsAppName || '', controller: scope.controller || '', action: scope.action || '', id: scope.id || ''}, ->


#
# A delete button which prompts the user to confirm their deletion.
# To use, simply pass the delete function to the "on-confirm" attribute
#
.directive 'confirmDelete', ->
  return {
    replace: true,
    templateUrl: 'templates/deleteConfirmation.html',
    scope: {
      onConfirm: '&'
    }
    controller: ($scope) ->
      $scope.isDeleting = false
      $scope.startDelete = ->
        $scope.isDeleting = true
      $scope.cancel = ->
        $scope.isDeleting = false
      $scope.confirm = ->
        $scope.onConfirm()
  }
