angular.module('pathway.directives', [])

  .directive('mcGraphContainer', ->
    defaultOptions = {
      Endpoint: [ 'Dot', { radius: 1 } ],
      Anchor: 'Continuous',
      Connector: 'Flowchart',
      ConnectorStyle: {
        strokeStyle: '#5c96bc',
        lineWidth: 2,
        outlineColor: 'transparent',
        outlineWidth: 4
      },
      ConnectionOverlays: [
        [
          'Arrow', {
            location: 1,
            id: 'arrow',
            length: 10,
            foldback: 1,
            width: 10
          }
        ]
      ],
      HoverPaintStyle: {
        strokeStyle: '#1e8151',
        lineWidth: 2
      },
      PaintStyle: {
        strokeStyle: '#5c96bc',
        lineWidth: 2,
        outlineColor: 'transparent',
        outlineWidth: 4
      }
    }
    return {
    replace: false,
    scope: {
      options: '@'
    },
    link: (scope, iElement, iAttrs) ->
      if scope.options
        jsPlumb.importDefaults(scope.options)
      else
        jsPlumb.importDefaults(defaultOptions)
    }
  )# end directive

  # Handle the nodes
  .directive('mcGraphNode',  ->
    return {
    replace: true,
    transclude: true,
    requires: '^mcGraphContainer', # Tie this directive to mcGraphContainer
    scope: {
      node: '=graphNode',
      isSelected: '&',
      selectNode: '&'
    },
    templateUrl: 'templates/pathway/jsPlumbNode.html',
    link: (scope, iElement, iAttrs) ->

      jsPlumb.makeSource($('.ep', iElement), {
        parent: iElement
      });

      jsPlumb.makeTarget(iElement);

      jsPlumb.draggable(iElement, {
        containment: 'parent',
        stop: (event, ui) ->
          scope.node.y = Math.floor(ui.position.top);
          scope.node.x = Math.floor(ui.position.left);

      });

    }
  )# End of directive

  #Handle the links
  .directive('mcGraphLink', ->
    return {
    restrict: 'A',
    requires: '^graphContainer', #Tie this directive to graphContainer
    scope: {
      link: '=graphLink',
    },
    link: (scope, iElement, iAttrs) ->
      #FIXME: Needed the timeout to make sure the dom nodes are available. Need a better solution.
      setTimeout(->
        jsPlumb.connect({
          source: "node" + scope.link.source,
          target: "node" + scope.link.target,
          parameters: {
            connectionId: scope.link.id
          }
        }).canvas.id = scope.link.id; # Give the resulting svg node an id for simpler retrieval
      , 1)
    }
  )