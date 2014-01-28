pathwayEditor = angular.module('pathway.controllers', ['pathway.services'])

.controller('PathwayEditorCtrl', ['$scope', 'Grails', 'NodeSelector', ($scope, Grails, NodeSelector) ->
        $scope.pathway = Grails.getResource($scope).get {action: 'show'}

        $scope.selectNode = (node) ->
            NodeSelector.selectNode(node)
        $scope.isSelected = (node) ->
            NodeSelector.isSelected(node)


        $scope.deleteNode = (node) ->

            # 1. Remove the node
            index = $scope.pathway.nodes.indexOf(node)
            $scope.pathway.nodes.splice(index, 1);

            # 2. Remove the links associated with the node from the model
            angular.forEach($scope.pathway.links, (link)->
                if link.source == node.id || link.target == node.id
                    index = $scope.pathway.links.indexOf(link)
                    $scope.pathway.links.splice(index, 1)
            )

            # 3. Prompt jsPlumb to remove the links connecting the node
            jsPlumb.detachAllConnections($('#node' + node.id))

            # 4. Update the NodeSelector so it doesn't reference the old object
            NodeSelector.selectNode(null)

        $scope.addNode = ->
            $scope.pathway.nodes.push({
                name: "New node"
                id: "LOCAL" + nextLocalId()
                x: 100 + (Math.random()*300)
                y: 100 + (Math.random()*300)
            })

        lastLocalId = 0
        nextLocalId = ->
            lastLocalId++
            return lastLocalId
    ])

.controller('NodePropertiesCtrl', ['$scope', 'NodeSelector', ($scope, NodeSelector) ->
        $scope.selectedNode = null
        $scope.switchToSubPathway = ->
            console.log("FIXME: this should switch the pathway viewer's scope to node " + $scope.selectedNode.id)

        $scope.deleteNode = ->
            $scope.$parent.deleteNode($scope.selectedNode)

        $scope.removeForm = (form) ->
            console.log("FIXME: this should remove form " + form.id + " from node " + $scope.selectedNode.id)

        $scope.addForm = ->
            console.log("FIXME: this should add a form to node " + $scope.selectedNode.id)

        $scope.removeDataElement = (dataElement) ->
            console.log("FIXME: this should remove the dataElement " + dataElement.id + " from node " + $scope.selectedNode.id)

        $scope.addDataElement = ->
            console.log("FIXME: this should add a dataElement to node " + $scope.selectedNode.id)

        # Watch the NodeSelector function for changes. The second function actions a change, setting the
        # selectedNode scope variable to be the new value retrieved from the service
        $scope.$watch(->
            NodeSelector.getSelectedNode()
        , (selectedNode) ->
            $scope.selectedNode = selectedNode
        , false # Just check for object equality
        )
    ])

.controller('GraphCanvasCtrl', ['$scope', 'NodeSelector', ($scope, NodeSelector) ->
        $scope.pathway
        $scope.selectNode = (node) ->
            NodeSelector.selectNode(node)
        $scope.isSelected = (node) ->
            NodeSelector.isSelected(node)
    ])

