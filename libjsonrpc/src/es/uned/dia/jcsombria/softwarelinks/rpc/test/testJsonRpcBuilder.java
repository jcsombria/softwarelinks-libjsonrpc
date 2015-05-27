package es.uned.dia.jcsombria.softwarelinks.rpc.test;
import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Test;

import es.uned.dia.jcsombria.softwarelinks.rpc.JsonRpcBuilder;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParam;

public class testJsonRpcBuilder {
  
	@Test
	public void testCanBuildRequestWithoutParams() {
		String method = "test";
		String id = "1234";
		Object[] params = null;
		String expectedRequest = "{" +
			"\"jsonrpc\":\"2.0\"" +
			",\"method\":\"" + method + "\"" +
			",\"id\":\"" + id + "\"" +
		"}";
		String request = JsonRpcBuilder.request(method, (RpcParam[])params, id).toString();
		assertEquals(expectedRequest, request);
	}

	@Test
	public void testCanBuildResponseWithInteger() {
		int result = 1;
		String id = "1234";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":1,\"id\":\"1234\"}";		
		JsonObject response = JsonRpcBuilder.response(result, id);
		assertEquals(expected, response.toString());
	}

	@Test
	public void testCanBuildResponseWithDouble() {
		double result = 1.0;
		String id = "1234";	
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":"+result+",\"id\":\"1234\"}";
		JsonObject response = JsonRpcBuilder.response(result, id);
		assertEquals(expected, response.toString());
	}

	@Test
	public void testCanBuildResponseWithString() {
		String result = "hello world";
		String id = "1234";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":\"hello world\",\"id\":\"1234\"}";
		JsonObject response = JsonRpcBuilder.response(result, id);
		assertEquals(expected, response.toString());
	}

	@Test
	public void testCanBuildResponseWithArray() {
		double[] result = {1.0, 2.0};
		String id = "1234";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":[1.0,2.0],\"id\":\"1234\"}";
		JsonObject response = JsonRpcBuilder.response(result, id);
		assertEquals(expected, response.toString());
	}

	@Test
	public void testCanBuildResponseWithDoubleArray() {
		double[] result = {1.0, 2.0};
		String id = "1234";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":[1.0,2.0],\"id\":\"1234\"}";
		JsonObject response = JsonRpcBuilder.response(result, id);
		System.out.println(response);
		assertEquals(expected, response.toString());
	}
}