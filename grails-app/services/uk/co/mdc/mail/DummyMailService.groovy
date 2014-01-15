package uk.co.mdc.mail

import org.springframework.mail.MailMessage

class DummyMailService {
	MailMessage sendMail(Closure callable) {
		callable.delegate = new CallPrinter()
		callable.call()
		return null
	}
}

class CallPrinter {
	def to(String recipient){
		log.debug "To: ${recipient}"
	}
	def from(String sender){
		log.debug "From: ${sender}"
	}
	def subject(String subject){
		log.debug "Subject: ${subject}"
	}
	def html(html){
		log.debug "HTML content: ${html}"
	}
	def body(body){
		log.debug "Body content: ${body}"
	}
}
