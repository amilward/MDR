package uk.co.mdc.pages.pathways

import geb.Module

/**
 * Created by rb on 18/12/2013.
 */
class PathwayEditModal extends Module {
    static content = {
        nameInput { $("#txt-nameUpdate")}
        descriptionInput { $("#txt-descUpdate")}
        versionNoInput { $("#txt-versionNoUpdate")}
        draftInput { $("#select-isDraftUpdate")}

        deleteButton { 	$("#deletePathway") }
        deleteConfirmationButton { $("#confirmDeletePathway")}
        deleteCancellationButton { $("#cancelDeletePathway")}
    }
}
