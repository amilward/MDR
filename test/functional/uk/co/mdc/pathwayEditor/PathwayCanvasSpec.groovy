package uk.co.mdc.pathwayEditor

import geb.spock.GebReportingSpec

/**
 * Created by rb on 17/01/2014.
 */
class PathwayCanvasSpec extends GebReportingSpec{

    /** node related bits **/
    //When: a node is selected
    //Then: the node should be highlighted in the tree view
    //And: The node should be highlighted on the canvas
    //And: The node's properties should be displayed on the properties panel

    //When: I double click on the canvas
    //Then: a node is created and selected under the cursor

    //When: I double click on a node
    //Then: the text in that node is selected
    //And: the text can be edited
    //And: pressing "return" will end the edit and save
    //And: pressing "escape" will cancel the edit

    //When: I double click on a node
    //Then: the text in that node is selected
    //And: the text can be edited
    //And: clicking outside the node will end the edit and save
    //And: clicking on a small 'x' will cancel the edit

    //When: I drag a node
    //Then: the node's new position should be saved to the server

    /** link related bits **/
    //When: I drag from a connection endpoint on a node to another connection endpoint
    //Then: a link should be made between those endpoints and saved to the server

    /** selection and cloning **/
    // when: I click on a node or link
    // then: it is selected

    // when: I drag a selection box around some links and nodes
    // then: any nodes or links completely enclosed by the selection box are highlighted

    // given: I have selected some nodes on the canvas
    // when: I use the system copy command
    // then: the selected items are copied to the clipboard
    // when: I use the system paste command
    // then: the items on the clipboard are cloned onto the canvas

    // given: I have selected one or more nodes and/or links on the canvas
    // when: I press the delete key
    // then: the selected items are deleted

    // given: I have selected one or more nodes and/or links on the canvas
    // when: I press the delete button on the editor
    // then: the selected items are deleted
}
