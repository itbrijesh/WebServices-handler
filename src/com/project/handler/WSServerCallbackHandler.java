package com.project.handler;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidationException;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PlainTextPasswordRequest;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.Request;


public class WSServerCallbackHandler implements CallbackHandler {

	private static final String username = "Pramukh";
	private static final String password = "password";
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		
		for( Callback callback: callbacks ) {
			
			if( callback instanceof PasswordValidationCallback ) {
				
				PasswordValidationCallback validator = (PasswordValidationCallback) callback;
				
				if( validator.getRequest() instanceof PasswordValidationCallback.PlainTextPasswordRequest ) {
					
					validator.setValidator( new PlainTextPasswordVerifier() );
				}
			}
			else {
				throw new UnsupportedCallbackException( null, "Not Needed (from server handler)" );
			}
		}
					
	}
	
	private class PlainTextPasswordVerifier implements PasswordValidationCallback.PasswordValidator {

		@Override
		public boolean validate(Request request) throws PasswordValidationException {
			
			PasswordValidationCallback.PlainTextPasswordRequest plainValidator = 
					(PlainTextPasswordRequest) request;
			
			return username.equalsIgnoreCase( plainValidator.getUsername() ) &&
					 password.equals( plainValidator.getPassword() );
		}
		
	}

}
