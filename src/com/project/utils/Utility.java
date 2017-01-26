package com.project.utils;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.soap.SOAPFaultException;

public class Utility {

	public static void generateSOAPErrMessage(SOAPMessage msg, String reason) {
	       try {
	    	  System.out.println("----error----soap---error---message----");
	          SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
	          
	          SOAPFault soapFault = soapBody.addFault();
	          soapFault.setFaultString(reason);
	          
	          System.out.println("----error----soap---error---message----");
	          throw new SOAPFaultException(soapFault); 
	       }
	       catch(SOAPException e) { }
	    }
	
}
