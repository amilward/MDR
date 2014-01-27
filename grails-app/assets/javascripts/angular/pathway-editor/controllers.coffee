pathwayEditor = angular.module('pathway.controllers',['pathway.services'])

.controller('PathwayEditorCtrl', ['$scope', 'Grails', 'NodeSelector', ($scope, Grails, NodeSelector) ->
  $scope.pathway = Grails.getResource($scope).get {action: 'show'}

  $scope.selectNode = (node) ->
    NodeSelector.selectNode(node)
  $scope.isSelected = (node) ->
    NodeSelector.isSelected(node)
])

.controller('NodePropertiesCtrl', ['$scope', 'NodeSelector', ($scope, NodeSelector) ->
  $scope.selectedNode = null
  $scope.switchToSubPathway = ->
    console.log("FIXME: this should switch the pathway viewer's scope to node " + $scope.selectedNode.id)

  $scope.delete = ->
    console.log("FIXME: this should delete the node " + $scope.selectedNode.id)

  $scope.removeForm = (form) ->
    console.log("FIXME: this should remove form " + form.id + " from node "  + $scope.selectedNode.id )

  $scope.addForm = ->
    console.log("FIXME: this should add a form to node "+ $scope.selectedNode.id)

  $scope.removeDataElement = (dataElement) ->
    console.log("FIXME: this should remove the dataElement " + dataElement.id + " from node "  + $scope.selectedNode.id)

  $scope.addDataElement = ->
    console.log("FIXME: this should add a dataElement to node "  + $scope.selectedNode.id)

  # Watch the NodeSelector function for changes. The second function actions a change, setting the
  # selectedNode scope variable to be the new value retrieved from the service
  $scope.$watch( ->
      NodeSelector.getSelectedNode()
    , (selectedNode) ->
      $scope.selectedNode = selectedNode
    , false # Just check for object equality
  )
])

.controller('GraphCanvasCtrl', ['$scope', 'NodeSelector', ($scope, NodeSelector) ->
  $scope.selectNode = (node) ->
    NodeSelector.selectNode(node)
  $scope.isSelected = (node) ->
    NodeSelector.isSelected(node)
])


# Example of how to set additional variables based on the response from the resource
# pathwayEditor.controller('PathwayEditorCtrl', ['$scope', 'Grails', ($scope, Grails) ->
#   $scope.pathway = Grails.getResource($scope).get {action: 'show'}, (pathway) ->
#     $scope.topPathway = pathway
# ])
