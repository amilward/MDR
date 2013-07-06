package groovy_metadata_registry

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.*
 
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
 
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * @date created on 2011/03/25 William Moore
 *
 * @version $Revision: $ $Date: $ $Author: $
 *
 * @class APIAuthenticationFilters
 * @brief This class adds a filter to handle URLs where the API credentials
 *        are supplied with the REST request
 *
 */
class APIAuthenticationFilters extends GenericFilterBean implements ApplicationEventPublisherAware {
	
	def filters = {
	}
	
	def authenticationManager
	def eventPublisher
	def rememberMeServices
	def springSecurityService
	AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler()
	AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler()
	 
	void afterPropertiesSet() {
		assert authenticationManager != null, 'authenticationManager must be specified'
		assert rememberMeServices != null, 'rememberMeServices must be specified'
	}
	 
	void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req
		HttpServletResponse response = (HttpServletResponse) res
		 
		 
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			def username = request.getParameter("apikey")
			def password = request.getParameter("apisec")
			 
			Authentication auth
			UsernamePasswordAuthenticationToken upat
			 
			if ( username && password ) {
				try {
					upat = new UsernamePasswordAuthenticationToken(username, password)
					 
					if (upat != null) {
						auth = authenticationManager.authenticate(upat)
						 
						logger.debug("Authentication success: " + auth);
						 
						onSuccessfulAuthentication(request, response, auth)
					}
				} catch (AuthenticationException authenticationException) {
					onUnsuccessfulAuthentication(request, response, authenticationException)
				} catch(e) {
					onUnsuccessfulAuthentication(request, response, e)
				}
			}
		}
		chain.doFilter(req, res)
	}
	 
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
		SecurityContextHolder.getContext().setAuthentication(authResult)
		rememberMeServices.onLoginSuccess(request, response, authResult)
	}
	 
	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		SecurityContextHolder.clearContext();
		rememberMeServices.loginFail(request, response)
	}
	 
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher
	}
}
