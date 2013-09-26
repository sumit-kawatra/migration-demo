/**
 * 
 */
package com.markitserv.msws.commands;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.command.AsyncCommand;

/**
 * @author kiran.gogula
 *
 */

public class EmailCommand implements AsyncCommand{
	
    	private static final long serialVersionUID = 1L;

		private String to;
		
		private String from;
		
		private String subject;
		
		private String body;
		


		public EmailCommand() {
			super();
		}
		
		public EmailCommand(String to, String from , String subject, String body){
			super();
			this.to = to;
			this.from = from;
			this.subject = subject;
			this.body = body;
		}
		
		
		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}
		
		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this,
			ToStringStyle.MULTI_LINE_STYLE);
		}
		
	

}
