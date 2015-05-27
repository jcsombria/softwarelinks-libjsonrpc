package es.uned.dia.jcsombria.softwarelinks.rpc.test;
import static org.junit.Assert.*;

import org.junit.Test;

import es.uned.dia.jcsombria.softwarelinks.rpc.JsonRpcServer;

public class testJsonRpcServer {

	@Test
	public void testServerCanParseRequest() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"error\":{\"code\":-32601,\"message\":\"Method not found\"},\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}
	
	@Test
	public void testCanCallMethodWithoutArguments() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoNoArgs\",\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":null,\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}


	@Test
	public void testCanCallMethodWithMultipleArguments() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoTwoArgs\",\"params\":[\"hello \",\"world\"],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":\"hello world\",\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendString() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[\"hello world\"],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":\"hello world\",\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendInt() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[1],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":1,\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendDouble() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[1.0],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":1.0,\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendDoubleArray() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[[1.0,2.0]],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":[1.0,2.0],\"id\":\"1234\"}";
		System.out.println(request);
		String result = server.parse(request);
		System.out.println(result);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendTrue() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[true],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":true,\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendFalse() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[false],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":false,\"id\":\"1234\"}";
		String result = server.parse(request);
		assertEquals(expected, result);
	}

	@Test
	public void testCanSendNull() {
		JsonRpcServer server = new JsonRpcServer();
		String request = "{\"jsonrpc\":\"2.0\",\"method\":\"echoOneArg\",\"params\":[null],\"id\":\"1234\"}";
		String expected = "{\"jsonrpc\":\"2.0\",\"result\":null,\"id\":\"1234\"}";
		Object result = server.parse(request);
		assertEquals(expected, result.toString());
	}

}