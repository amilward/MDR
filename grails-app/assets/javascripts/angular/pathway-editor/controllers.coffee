pathwayEditor = angular.module('pathway.controllers',['pathway.services'])

.controller('PathwayEditorCtrl', ['$scope', 'Grails', 'NodeSelector', ($scope, Grails, NodeSelector) ->
  $scope.pathway = Grails.getResource($scope).get {action: 'show'}

  $scope.selectNode = (node) ->
    NodeSelector.selectNode(node)
    $rootScope.$broadcast('mcPathwayNodeSelected', node);
  $scope.isSelected = (node) ->
    NodeSelector.isSelected(node)

])

.controller('NodePropertiesCtrl', ['$scope', 'NodeSelector', ($scope, NodeSelector) ->

  # Watch the NodeSelector function for changes. The second function actions a change, setting the
  # selectedNode scope variable to be the new value retrieved from the service
  $scope.$watch( ->
      NodeSelector.getSelectedNode()
    , (selectedNode) ->
      $scope.selectedNode = selectedNode
    , false # Just check for object equality
  )
])


# Example of how to set additional variables based on the response from the resource
# pathwayEditor.controller('PathwayEditorCtrl', ['$scope', 'Grails', ($scope, Grails) ->
#   $scope.pathway = Grails.getResource($scope).get {action: 'show'}, (pathway) ->
#     $scope.topPathway = pathway
# ])
