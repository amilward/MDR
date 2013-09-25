package uk.co.mdc


class SecUser {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Date lastLoginDate
	CollectionBasket collectionBasket = new CollectionBasket();
	

	static constraints = {
		username blank: false, unique: true
		password blank: false
		lastLoginDate nullable: true
	}

	static mapping = {
		password column: '`password`'
	}
	
	static hasOne = {collectionBasket: CollectionBasket}

	Set<SecAuth> getAuthorities() {
		SecUserSecAuth.findAllBySecUser(this).collect { it.secAuth } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
