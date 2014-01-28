angular.module('utils', [])


.directive('confirmDelete', ->
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
)# End of directive
