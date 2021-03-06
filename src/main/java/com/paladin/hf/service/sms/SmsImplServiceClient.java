
package com.paladin.hf.service.sms;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class SmsImplServiceClient {

	private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
	private HashMap endpoints = new HashMap();
	private Service service0;

	public SmsImplServiceClient(String url) {
		create0();
		Endpoint SmsServiceLocalEndpointEP = service0.addEndpoint(new QName("http://webservice.service.netmatch.com/", "SmsServiceLocalEndpoint"),
				new QName("http://webservice.service.netmatch.com/", "SmsServiceLocalBinding"), "xfire.local://SmsServiceImplService");
		endpoints.put(new QName("http://webservice.service.netmatch.com/", "SmsServiceLocalEndpoint"), SmsServiceLocalEndpointEP);
		Endpoint SmsService4XMLImplPortEP = service0.addEndpoint(new QName("http://webservice.service.netmatch.com/", "SmsServiceImplPort"),
				new QName("http://webservice.service.netmatch.com/", "SmsServiceSoapBinding"), url);
		endpoints.put(new QName("http://webservice.service.netmatch.com/", "SmsServiceImplPort"), SmsService4XMLImplPortEP);
	}

	public Object getEndpoint(Endpoint endpoint) {
		try {
			return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
		} catch (MalformedURLException e) {
			throw new XFireRuntimeException("Invalid URL", e);
		}
	}

	public Object getEndpoint(QName name) {
		Endpoint endpoint = ((Endpoint) endpoints.get((name)));
		if ((endpoint) == null) {
			throw new IllegalStateException("No such endpoint!");
		}
		return getEndpoint((endpoint));
	}

	public Collection getEndpoints() {
		return endpoints.values();
	}

	private void create0() {
		TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
		HashMap props = new HashMap();
		props.put("annotations.allow.interface", true);
		AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
		asf.setBindingCreationEnabled(false);
		service0 = asf.create((SmsService.class), props);

		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webservice.service.netmatch.com/", "SmsServiceSoapBinding"),
					"http://schemas.xmlsoap.org/soap/http");
		}
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webservice.service.netmatch.com/", "SmsServiceLocalBinding"),
					"urn:xfire:transport:local");
		}
	}

	public SmsService getSmsServiceLocalEndpoint() {
		return ((SmsService) (this).getEndpoint(new QName("http://webservice.service.netmatch.com/", "SmsServiceLocalEndpoint")));
	}

	public SmsService getSmsServiceLocalEndpoint(String url) {
		SmsService var = getSmsServiceLocalEndpoint();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public SmsService getSmsService4XMLImplPort() {
		return ((SmsService) (this).getEndpoint(new QName("http://webservice.service.netmatch.com/", "SmsServiceImplPort")));
	}

	public SmsService getSmsService4XMLImplPort(String url) {
		SmsService var = getSmsService4XMLImplPort();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}


}
