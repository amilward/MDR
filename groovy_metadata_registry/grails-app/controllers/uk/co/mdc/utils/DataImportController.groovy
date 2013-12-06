package uk.co.mdc.utils

import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ADMIN'])
class DataImportController {

    def importDataSet() { 
		def datasets = params.datasets
		
		for(dataset in datasets){
			if(dataset == "nhic"){
				importerNHICService.import()
			}
		}
	}
}
