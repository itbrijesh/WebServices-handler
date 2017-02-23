package com.project.endpoint;

import javax.xml.ws.Endpoint;

import com.project.ws.HelloWS;
import com.project.ws.ServerInfo;

public class WSPublisher {

	public static void main( String ...args )
	{
		Endpoint.publish("http://localhost:8888/ws/server", new HelloWS() );
		
		System.out.println(" Service is published ! ");
	}
}
