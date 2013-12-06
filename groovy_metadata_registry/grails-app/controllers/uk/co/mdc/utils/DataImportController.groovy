package uk.co.mdc.utils

import org.springframework.transaction.annotation.Transactional
import grails.plugins.springsecurity.Secured;


class DataImportController {

	static allowedMethods = [importDataSet: "GET", ]
	
	def importNHICService

    def importDataSet() { 
		def dataset = params.dataset
		Status state = Status.FAILED

		if(dataset == "nhic"){
			importNHICService.importData()
			state = Status.COMPLETE
		}
		
		/**
		 * Clean up and redirect the user
		 */
		if(state == Status.PENDING){
			flash.message = "dataimport.pleaseWait"
			flash.args = [dataset]
			flash.default = "Process started. Please come back later"
		}
		else if(state == Status.COMPLETE){
			flash.message = "dataimport.complete"
			flash.args = [dataset]
			flash.default = "Process complete"
		}
		else{
			flash.message = "dataimport.paramError"
			flash.args = [dataset]
			flash.default = "Error: invalid dataset"
		}
		render(view:"/admin/importData")
	}
	
	/**
	 * Simple enum to record the state of the process. Might need to be
	 * moved into domain model once we get asynchronous support in Grails 2.3
	 * @author Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
	 *
	 */
	enum Status{
		PENDING,
		FAILED,
		COMPLETE
	}
}
