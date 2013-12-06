hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = false
	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"

			//  if using mysql driver replace the url above with
			//	the mysql url for the database i.e.
			//	url = "jdbc:mysql://localhost/mdr?useUnicode=yes&characterEncoding=UTF-8"


		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
		}
	}
	
	/**
	 * Staging settings.
	 * 
	 * These settings mirror the production environment in all *except* the database is dropped and re-created for each run.
	 * This is to make staging deployments easier.
	 * 
	 * Requires 'url', 'username' and 'password' fields. These should NOT be stored in the main application repository.
	 * Instead store them in ~/.grails/model_catalogue-config.groovy and they will be loaded at runtime.
	 */
	staging {
		dataSource {
			dbCreate = "create-drop"
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			pooled = true
			properties {
				maxActive = -1
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=true
				validationQuery="SELECT 1"
			}
		}
	}

	/**
	 * Production settings.
	 * 
	 * IMPORTANT! This environment doesn't update the database schema at all. This is sensible! It does mean if we have version
	 * bump we need to manually upgrade the database, but this protects us from a) out-of-date columns remaining and b) catastrophe!
	 *
	 * Requires 'url', 'username' and 'password' fields. These should NOT be stored in the main application repository.
	 * Instead store them in ~/.grails/model_catalogue-config.groovy and they will be loaded at runtime.
	 */
	production {
		dataSource {
			// FIXME we should remove dbCreate and use the database-migration plugin... or should we? If we're deploying a new instance rather than upgrading, this may not be necessary.
			dbCreate = "update"
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			pooled = true
			properties {
				maxActive = -1
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=true
				validationQuery="SELECT 1"
			}
		}
	}
	
}
