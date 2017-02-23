package com.project.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;

public class WSSecurityServerHandler implements SOAPHandler<SOAPMessageContext>{

	private XWSSProcessor processor = null;
	
	public WSSecurityServerHandler() {
		
		try {
			
			XWSSProcessorFactory factory = XWSSProcessorFactory.newInstance();
			processor = factory.createProcessorForSecurityConfiguration( 
						new FileInputStream( new File("src/server.xml")) , new WSServerCallbackHandler());
			System.out.println( "Initialization of XWSSProcessor is finished." ); 
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException( e.getMessage() );
		}
		catch (XWSSecurityException e) {
			e.printStackTrace();
			throw new RuntimeException( e.getMessage() );
		}
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		Boolean outbond = (Boolean) context.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );
		
		if( !outbond ) {
			
			SOAPMessage message = context.getMessage();
			
			try {
				System.out.println("Setting the message need to be verify...");
				// First set the message you want to process.
				ProcessingContext processingContext = processor.createProcessingContext( message );
				processingContext.setSOAPMessage( message );
				
				System.out.println("Preparing verification message...");
				// Verify above message
				SOAPMessage verifiedMessage = processor.verifyInboundMessage( processingContext );
				
				context.setMessage( verifiedMessage );
				
				System.out.println("Message verification has been done...");
			} 
			catch (XWSSecurityException e) {
				e.printStackTrace();
				throw new RuntimeException( e.getMessage() );
			}
		}
		
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		
		String uri = "http://docs.oasis-open.org/wss/2004/01/"
				+ "oasis-200401-wss-wssecurity-secext-1.0.xsd";
		
		QName qname = new QName( uri, "Security", "wsse");
		Set<QName> headers = new HashSet<>();
		headers.add( qname );
		
		return headers;
	}

}
