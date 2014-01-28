'use strict';

describe 'service', ->
    setup = {}
    beforeEach module('pathway.services')

    describe 'NodeSelector', ->
        it 'should return null by default', inject (NodeSelector) ->
          expect(NodeSelector.getSelectedNode()).toBeNull()


        it 'should return the selected node', inject (NodeSelector) ->
            node = "node"
            NodeSelector.selectNode(node)
            expect(NodeSelector.getSelectedNode()).toBe(node)

        it 'should return allow the selected node to be changed', inject (NodeSelector) ->
            node = "node"
            node2 = "node2"
            NodeSelector.selectNode(node)
            expect(NodeSelector.isSelected(node)).toBe(true)
            expect(NodeSelector.isSelected(node2)).toBe(false)
            expect(NodeSelector.getSelectedNode()).toBe(node)
            expect(NodeSelector.getSelectedNode()).not.toBe(node2)


            NodeSelector.selectNode(node2)
            expect(NodeSelector.isSelected(node2)).toBe(true)
            expect(NodeSelector.isSelected(node)).toBe(false)
            expect(NodeSelector.getSelectedNode()).toBe(node2)
            expect(NodeSelector.getSelectedNode()).not.toBe(node)
