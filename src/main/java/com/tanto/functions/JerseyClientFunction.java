package com.tanto.functions;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class JerseyClientFunction {
	public static ObjectMapper mapper = new ObjectMapper();
	public static Client client = Client.create();
	
	public JsonNode clientPost(String resource, HashMap<String, Object> data) throws UniformInterfaceException, ClientHandlerException, JsonProcessingException {
		
		client.addFilter(new HTTPBasicAuthFilter("elastic", "Br1m0b2020"));
		WebResource wr = client.resource(resource);
		ClientResponse response = wr.type("application/json").post(ClientResponse.class, mapper.writeValueAsString(data));
		
		String output = response.getEntity(String.class);
		JsonNode node = mapper.readValue(output, JsonNode.class);
		
		return node;
	}

	
	public JsonNode clientDelete(String resource) throws JsonMappingException, JsonProcessingException {
		
		client.addFilter(new HTTPBasicAuthFilter("elastic", "Br1m0b2020"));
		WebResource wr = client.resource(resource);
		ClientResponse response = wr.type("application/json").delete(ClientResponse.class);
		
		String output = response.getEntity(String.class);
		JsonNode node = mapper.readValue(output, JsonNode.class);
		
		return node;
	}
	
	public JsonNode clientGet(String resource) throws JsonMappingException, JsonProcessingException {
		client.addFilter(new HTTPBasicAuthFilter("elastic", "Br1m0b2020"));
		WebResource wr = client.resource(resource);
		ClientResponse response = wr.type("application/json").get(ClientResponse.class);
		
		String output = response.getEntity(String.class);
		JsonNode node = mapper.readValue(output, JsonNode.class);
		
		return node;
	}
}
