package uk.co.mdc

import org.apache.commons.lang.builder.HashCodeBuilder

class SecUserSecAuth implements Serializable {

	SecUser secUser
	SecAuth secAuth

	boolean equals(other) {
		if (!(other instanceof SecUserSecAuth)) {
			return false
		}

		other.secUser?.id == secUser?.id &&
			other.secAuth?.id == secAuth?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (secUser) builder.append(secUser.id)
		if (secAuth) builder.append(secAuth.id)
		builder.toHashCode()
	}

	static SecUserSecAuth get(long secUserId, long secAuthId) {
		find 'from SecUserSecAuth where secUser.id=:secUserId and secAuth.id=:secAuthId',
			[secUserId: secUserId, secAuthId: secAuthId]
	}

	static SecUserSecAuth create(SecUser secUser, SecAuth secAuth, boolean flush = false) {
		new SecUserSecAuth(secUser: secUser, secAuth: secAuth).save(flush: flush, insert: true)
	}

	static boolean remove(SecUser secUser, SecAuth secAuth, boolean flush = false) {
		SecUserSecAuth instance = SecUserSecAuth.findBySecUserAndSecAuth(secUser, secAuth)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(SecUser secUser) {
		executeUpdate 'DELETE FROM SecUserSecAuth WHERE secUser=:secUser', [secUser: secUser]
	}

	static void removeAll(SecAuth secAuth) {
		executeUpdate 'DELETE FROM SecUserSecAuth WHERE secAuth=:secAuth', [secAuth: secAuth]
	}

	static mapping = {
		id composite: ['secAuth', 'secUser']
		version false
	}
}
