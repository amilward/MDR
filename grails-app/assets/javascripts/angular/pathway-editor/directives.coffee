angular.module('pathway.directives', [])

  #
  # Tree View directive,
  .directive('mcTreeView', [ ->
      return {
        templateUrl: 'pathwayTreeView.html'
        scope: {
          node: '=pathway',
          isSelected: '&',
          selectNode: '&'
        }
      }
    ])

  .directive('graphContainer', ->
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
    restrict: 'A',
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
  .directive('graphNode', ->
    return {
    restrict: 'A',
    replace: true,
    transclude: true,
    requires: '^graphContainer', # Tie this directive to graphContainer
    scope: {
      node: '=graphNode',
      epClass: '@'
    },
    #templateUrl: 'templates/graph-node.html',
    template: '<div class="node" id="node_{{node.id}}" style="left: {{node.x}}; top: {{node.y}}">' +
    '<div ng-transclude></div>' +
    '<div class="fa fa-chevron-right ep right">' +
    '</div><div class="fa fa-chevron-left ep left"></div>' +
    '<div class="fa fa-chevron-up ep up"></div>' +
    '<div class="fa fa-chevron-down ep down"></div>' +
    '</div>',
    link: (scope, iElement, iAttrs) ->
      ep = '.' + (scope.epClass || 'ep');
      iElement.on('mouseover', ->
        $(ep, this).show();
      )
      iElement.on('mouseout', ->
        $(ep, this).hide();
      );

      jsPlumb.makeSource($(ep, iElement), {
        parent: iElement
      });

      jsPlumb.makeTarget(iElement);

      jsPlumb.draggable(iElement, {
        containment: 'parent',
        stop: (event, ui) ->
          scope.node.y = Math.floor(ui.position.top) + 'px';
          scope.node.x = Math.floor(ui.position.left) + 'px';

      });

    }
  )# End of directive

  #Handle the links
  .directive('graphLink', ->
    return {
    restrict: 'A',
    requires: '^graphContainer', #Tie this directive to graphContainer
    scope: {
      source: '@',
      target: '@',
      id: '@'
    },
    link: (scope, iElement, iAttrs) ->
      #FIXME: Needed the timeout to make sure the dom nodes are available. Need a better solution.
      setTimeout(->
        jsPlumb.connect({
          source: "node_" + scope.source,
          target: "node_" + scope.target,
          parameters: {
            connectionId: scope.id
          }
        }).canvas.id = scope.id; # Give the resulting svg node an id for simpler retrieval
      , 1)
    }
  )