package es.uned.dia.jcsombria.softwarelinks.rpc.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.uned.dia.jcsombria.softwarelinks.rpc.JsonRpcClient;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParam;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamFactory;
import es.uned.dia.jcsombria.softwarelinks.transport.TestTransport;

public class testJsonRpcClient {
	private JsonRpcClient client = null;

	@Before
	public void setUp() {
		try {
			client = new JsonRpcClient(new TestTransport());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCanCallMethodWithoutArguments() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"id\":\"";
		RpcParam[] response = (RpcParam[])client.execute("test", null);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanCallMethodWithArguments() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[\"a\",1.0,1],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", "a"),
			RpcParamFactory.create("p2", 1.0),
			RpcParamFactory.create("p3", 1)
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendString() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[\"hello world\"],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", "hello world")
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendInt() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[1],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", 1)
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendDouble() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[1.0],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", 1.0)
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendTrue() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[true],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", true)
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendFalse() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[false],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", false)
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendNull() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[null],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.createNull("p1")
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}

	@Test
	public void testCanSendDoubleArray() {
		String expected = "{\"jsonrpc\":\"2.0\",\"method\":\"test\",\"params\":[[1.0,2.0]],\"id\":\"";
		RpcParam[] args = new RpcParam[] {
			RpcParamFactory.create("p1", new double[]{1.0, 2.0})
		};
		RpcParam[] response = (RpcParam[])client.execute("test", args);
		String result = ((RpcParam<String>)response[0]).get();
		assertTrue(result.startsWith(expected));
		assertTrue(result.endsWith("\"}"));
	}
}