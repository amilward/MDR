package uk.co.mdc


class SecUser {

	transient springSecurityService

	String username
	String password
	String email
	String firstName
	String lastName
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Date lastLoginDate
	ModelBasket modelBasket = new ModelBasket();
	

	static constraints = {
		username blank: false, unique: true
		password blank: false
		email nullable: true
		firstName nullable: true
		lastName nullable: true 
		lastLoginDate nullable: true
	}

	static mapping = {
		password column: '`password`'
	}
	
	static hasOne = [modelBasket: ModelBasket]

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
