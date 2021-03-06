package uk.co.mdc.pathways

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.acls.model.Permission
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LinkController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def linkService


	/* **************************************************************************************
	 * ************************************* GET LINK (JSON)***************************************
	 * get link and return as Json
	 ********************************************************************************************* */

	def getLinkJSON(Long id){

		def linkInstance = findInstance()
		def model

		if (!linkInstance) {

			model = [message: message(code: 'default.not.found.message', args: [
					message(code: 'Link.label', default: 'Link'),
					id
				])]
		}else{

			model = [linkInstance: linkInstance]
		}

		render model as JSON
	}

	/* **************************************************************************************
	 * ************************************* UPDATE LINK (JSON)***************************************
	 * update link and return as Json
	 ********************************************************************************************* */

	def updateLinkFromJSON(){

		def data = request.JSON
		def model
		
		if(data?.linkInstance?.id){

			def linkInstance = findInstance(data?.linkInstance?.id)

			if (!linkInstance) {
				def responseMessage = [errors: true, details: message(code: 'default.not.found.message', args: [
					message(code: 'linkInstance.label', default: 'Link'),
					linkId
				])]
				response.status = 400
				render responseMessage as JSON
				return
			}

			def linkVersion = data?.linkInstance?.linkVersionNo

			//check that we have the right version i.e. no one else has updated the form design whilst we have been
			//looking at it

			if (linkVersion != null) {
				if (linkInstance.version > linkVersion) {
					linkInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
							[
								message(code: 'formDesign.label', default: 'DataElement')] as Object[],
							"Another user has updated this Link while you were editing")
					model = [success: true, linkId: linkInstance.id, message: 'version number conflict, please reload page and try again']
					render model  as JSON
				}
			}

			linkInstance = linkService.update(linkInstance, data.linkInstance)

			if (linkInstance.errors.hasErrors()) {
				def responseMessage = [errors: true, details: linkInstance.errors]
				response.status = 400
				render responseMessage as JSON
				return
			}
			
			model = [success: true, linkId: linkInstance.id, linkVersion: linkInstance.version, message: 'saved']

		}else{

			 model = [errors: true, details: 'no id included']

		}

		render model  as JSON
	}

	
	/* **************************************************************************************
	 * ************************************* CREATE LINK (JSON)***************************************
	 * create link and return as Json
	 ********************************************************************************************* */

	def createLinkFromJSON(){

		def model
		def data = request.JSON
		def newLink = data.linkInstance

		def linkInstance = linkService.create(newLink)

		println(linkInstance)
				
		if(linkInstance){

			model = [success: true, linkId: linkInstance.id, pathwaysModelVersion: linkInstance.pathwaysModel.version,  linkVersion: linkInstance.version, message: 'saved']


		}else{

			model = [success: false]
		}

		render model  as JSON
	}

	/* **************************************************************************************
	 * ************************************* DELETE LINK (JSON)***************************************
	 * delete link and return as Json
	 ********************************************************************************************* */
	
	def deleteLink(Long id){
		def linkInstance = findInstance()
		def model
		def msg
		if (!linkInstance) {
			msg = message(code: 'default.not.found.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			model = [success: false, message: msg]
		}else{
			try {
				linkService.delete(linkInstance)
				msg = message(code: 'default.deleted.message', args: [
					message(code: 'link.label', default: 'Link'),
					id
				])
				model = [success: true, message: msg]
			}
			catch (DataIntegrityViolationException e) {
				msg = message(code: 'default.not.deleted.message', args: [
					message(code: 'link.label', default: 'Link'),
					id
				])
				model = [success: false, message: msg]
			}
		}

		render model as JSON
	}






	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	 * this function grant permission to the given link
	 *********************************************************************************** */


	def grant = {

		def link = findInstance()

		if (!link) return

			if (!request.post) {
				return [linkInstance: link]
			}

		linkService.addPermission(link, params.recipient, params.int('permission'))

		redirectShow "Permission $params.permission granted on Report $link.id " + "to $params.recipient", link.id
	}

	/* **********************************************************************************
	 * this function uses the link service to get the link so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */

	private Link findInstance() {
		def link = linkService.get(params.long('id'))
		if (!link) {
			flash.message = "Link not found with id $params.id"
			redirect action: list
		}
		link
	}

	private Link findInstance(id) {
		def link = linkService.get(id)
		if (!link) {
			flash.message = "Link not found with id $params.id"
			redirect action: list
		}
		link
	}




	/*FIXME*********These will need to be removed keeping them in for testing purposes***************/

	
	
	def index() {
		redirect(action: "list", params: params)
	}
	

	def create() {
		[linkInstance: new Link(params)]
	}

	def save() {
		def linkInstance = new Link(params)
		if (!linkInstance.save(flush: true)) {
			render(view: "create", model: [linkInstance: linkInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'link.label', default: 'Link'),
			linkInstance.id
		])
		redirect(action: "show", id: linkInstance.id)
	}


	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[linkInstanceList: Link.list(params), linkInstanceTotal: Link.count()]
	}

	def show(Long id) {
		def linkInstance = Link.get(id)
		if (!linkInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			redirect(action: "list")
			return
		}

		[linkInstance: linkInstance]
	}

	def edit(Long id) {
		def linkInstance = Link.get(id)
		if (!linkInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			redirect(action: "list")
			return
		}

		[linkInstance: linkInstance]
	}

	def update(Long id, Long version) {
		def linkInstance = Link.get(id)
		if (!linkInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (linkInstance.version > version) {
				linkInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'link.label', default: 'Link')] as Object[],
						"Another user has updated this Link while you were editing")
				render(view: "edit", model: [linkInstance: linkInstance])
				return
			}
		}

		linkInstance.properties = params

		if (!linkInstance.save(flush: true)) {
			render(view: "edit", model: [linkInstance: linkInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'link.label', default: 'Link'),
			linkInstance.id
		])
		redirect(action: "show", id: linkInstance.id)
	}

	def delete(Long id) {
		def linkInstance = Link.get(id)
		if (!linkInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			redirect(action: "list")
			return
		}

		try {
			linkInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'link.label', default: 'Link'),
				id
			])
			redirect(action: "show", id: id)
		}
	}



}
