package com.project.ws;

import java.util.Map;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService
@HandlerChain( file="handler-chain.xml" )
public class ServerInfo {

	@Resource
	WebServiceContext wctx;
	
	@WebMethod
	public String getServerName()
	{
		MessageContext msgContext = wctx.getMessageContext();
		
		Map requestHeaders = (Map) msgContext.get( MessageContext.HTTP_REQUEST_HEADERS );
		
		System.out.println( "Displaying Request Headers..." );
		System.out.println( requestHeaders );
		
		return "My Servername !";  
	}
	
	@WebMethod
	public int division( int a, int b ) {
		return a/b;
	}
}
 