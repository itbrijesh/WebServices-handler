package com.project.handler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.project.utils.ServerConstants;
import com.project.utils.Utility;

public class MacAddressValidatorHandler implements SOAPHandler<SOAPMessageContext> {

	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		System.out.println(" Server: Handle Message....");
		
		Boolean isRequest = (Boolean) context.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );
		
		// we need to process for incoming message only. isRequest return true to outbond message
		// returns false for inbond message
		
		if( !isRequest )
		{
			
			try {
			
				SOAPMessage message = context.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.getHeader();
				
				// If no header add one.
				if( header == null )
				{
					header = envelope.addHeader();
					
					Utility.generateSOAPErrMessage( message, "No header!" );
				}
				
				// Get client MAC Address from SOAP header.
				Iterator iterator = header.extractHeaderElements( SOAPConstants.URI_SOAP_ACTOR_NEXT );
				
				// If no header block then throw exception 
				if( iterator == null || !iterator.hasNext() )
				{
					Utility.generateSOAPErrMessage( message, "From---server--No Header Block for Next Actor" );
				}
				
				// If no MAC Address found then throw exception
				Node macNode = (Node) iterator.next();
				
				String macAddress = macNode != null ? macNode.getValue() : null ;
			
				if( macAddress == null )
				{
					Utility.generateSOAPErrMessage( message , "No MAC Address in header!" );
				}
				
				//if mac address is not match, throw exception
			    if(!macAddress.equals( ServerConstants.VERIFIED_MAC_ADDRESS ))
			    {
			    	Utility.generateSOAPErrMessage(message, "Invalid mac address, access is denied.");
			    }
			    
			    message.writeTo( System.out );
			} 
			catch (SOAPException | IOException e) {
				
				e.printStackTrace();
			}
		}
		
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {

		System.out.println(" Server handle fault...");
		return true;
	}

	@Override
	public void close(MessageContext context) {

		System.out.println("Server : close...");
	}

	@Override
	public Set<QName> getHeaders() {

		System.out.println(" Get Headers...");
		return null;
	}

}
