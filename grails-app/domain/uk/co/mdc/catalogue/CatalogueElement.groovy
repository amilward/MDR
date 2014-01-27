package uk.co.mdc.catalogue

import org.springframework.security.acls.domain.BasePermission

import java.util.List;
import grails.validation.ValidationException

abstract class CatalogueElement {

        enum Status {
            DRAFT, PENDING, FINALIZED, SUPERCEDED, REMOVED
        }


    //storage is used with propertyMissing to allow the user to extend any catalogue element properties
        def extension = [:]

    //version number - this gets iterated everytime a new version is created from a finalized version

        Integer versionNumber = 0
        Integer revisionNumber = 1

    //security services
        def aclUtilService
        def springSecurityService

        //status: once an object is finalized it cannot be changed
        //it's version number is updated and any subsequent update will
        //be applied to the next version
        Status status = Status.DRAFT
		
		static hasMany = { relations: Relationship }
		
		static constraints = {
		}
		
		static mapping = {
		}
		
		
		/******************************************************************************************************************/
		/**** allows you to add properties to all of the catalogue elements using the extension map************************************************/
		/******************************************************************************************************************/
		def propertyMissing(String name, value) { 
			extension[name] = value
		}
		
		
		def propertyMissing(String name) {
            extension[name]
		}
		
		
		
		/******************************************************************************************************************/
		/****functions for specifying relationships between catalogue elements using the Relationship class ************/
		/******************************************************************************************************************/
		
		/***********return all the relations************/
		
		List relations() {
		
			//array of relations to return to the caller
			def relationsR = []

			
			relations.each{ relation ->
				def objectId
                def relationshipDirection
                def relationshipType = relation.relationshipType
				
				if(relation.objectYId == this.id){
					objectId = relation.objectXId
                    if(relationshipType.xYRelationship){
                        relationshipDirection = relationshipType.xYRelationship
                    }
				}else{
					objectId = relation.objectYId
                    if(relationshipType.yXRelationship){
                        relationshipDirection = relationshipType.yXRelationship
                    }
				}
				
				def catalogueElement = CatalogueElement.get(objectId)
				catalogueElement.relationshipType = relationshipType
                catalogueElement.relationshipDirection = relationshipDirection
				relationsR.add(catalogueElement)
			}
		

			return relationsR
		
		}
		
		/***********return the relations with the given relationship type name***********/

		List relations(String relationTypeName) {
			
				//array of relations to return to the caller
				def relationsR = []

				
				if(relationTypeName){
				
					def relationshipType = RelationshipType.findByName(relationTypeName)
                    def relationshipDirection = relationshipType
                    if(relationshipType){
						
                        relations.each{ relation ->
                            if(relation.relationshipType.id==relationshipType.id){
                                def objectId

                                //if the relation y side is this object then return the x side of the relationship otherwise return the y side
                                if(relation.objectYId == this.id){
                                    objectId = relation.objectXId

                                    //if the relationship type has an xYRelationship then return this instead of the relationship type name
                                    //i.e. if the relationship is parentChild and the object to return is the parent, then return Parent rather
                                    //then ParentChild as the relationshipT type

                                    if(relationshipType.xYRelationship){
                                        relationshipDirection = relationshipType.xYRelationship
                                    }


                                }else{

                                    objectId = relation.objectYId

                                    //if the relationship type has an yXRelationship then return this instead of the relationship type name
                                    //i.e. if the relationship is parentChild and the object to return is the child, then return Child rather
                                    //then ParentChild as the relationship type

                                    if(relationshipType.yXRelationship){
                                        relationshipDirection = relationshipType.yXRelationship
                                    }



                                }



                                def catalogueElement = CatalogueElement.get(objectId)
                                catalogueElement.relationshipType = relationTypeName
                                catalogueElement.relationshipDirection = relationshipDirection
                                relationsR.add(catalogueElement)
                            }

                        }
                    }
				
				}
	
				return relationsR
			
			}
		
		
	
		public void addToRelations(Relationship relationship){
			this.relations.add(relationship)
		}

    /******************************************************************************************************************/
    /******   this method allows you to increment the version of any catalogue element, cloning it and creating ********
     ******   new relationships mirroring the old relationships  ******************************************************/
    /******************************************************************************************************************/

    def incrementVersion(){


        def clonedElement = this.getClass().newInstance()
        def properties = new HashMap(this.properties)

        //remove any relations form the properties map
        properties.remove('relations')
        properties.remove('version')

        //copy properties over to new object
        clonedElement.properties = properties

        //increment versionNumber of new object and reset status to draft

        clonedElement.status = CatalogueElement.Status.DRAFT

        clonedElement.save(flush:true, failOnError: true)

        def relations = this.relations()

        relations.each{ relation ->


            Relationship.link(clonedElement, relation, relation.relationshipType)

        }

        def supersession = RelationshipType.findByName("Supersession")
        Relationship.link(clonedElement, this, supersession)


        // Grant the current user principal administrative permission
        if(springSecurityService.authentication.name!='admin'){
            aclUtilService.addPermission clonedElement, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
        }

        //Grant admin user administrative permissions

        aclUtilService.addPermission clonedElement, 'admin', BasePermission.ADMINISTRATION

        return clonedElement

    }


    /******************************************************************************************************************/
    /*********************remove all the associated valueDomains and collections before deleting data element*****************************/
    /******************************************************************************************************************/

/*

    def prepareForDelete(){

        if(this.relations.size()!=0){

            dataForDelete = this.relations

            dataForDelete.each{ relationship->
                this.removeFromRelations(relationship)
            }
        }
    }


    }*/



}