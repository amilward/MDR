#
# Generic resource querier for Grails controllers. Surprisingly little code :), and all taken from
# http://claymccoy.blogspot.co.uk/2012/09/grails-with-angularjs-and-coffeescript.html
#

angular.module('pathway.services', ['ngResource'])
.service 'Grails', ($resource) ->
  getResource: (scope) ->
    $resource "/#{grailsAppName}/:controller/:action/:id.json ",
      {controller: scope.controller || '', action: scope.action || '', id: scope.id || ''}, ->

.service 'PathwayManager', ->
  pathway = null
  deleteNode: (node) ->
    index = pathway.nodes.indexOf(node)
    if index > -1
      pathway.nodes.splice(index, 1)

.service 'NodeSelector', ->
  selectedNode = null

  selectNode: (node) ->
    selectedNode = node

  isSelected: (node) ->
    return selectedNode == node

  getSelectedNode: ->
    selectedNode