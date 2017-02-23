package com.project.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService
@SOAPBinding( style=Style.DOCUMENT, parameterStyle=ParameterStyle.BARE, use=Use.LITERAL )
public class HelloWS {

	@WebMethod( operationName="sayHelloAnnonymous" )
	public String sayHello()
	{
		return "Jai Swaminarayan";
	}
	
	@WebMethod( operationName="sayHelloPerson" )
	
	//RequestWrapper( className="Hello" )
	//ResponseWrapper( className = "HelloResponse" )
	public String sayHello( @WebParam (name="name") String name )
	{
		return name;
	}
}
