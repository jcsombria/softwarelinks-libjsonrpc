/**
 * JsonRpcServer
 * author: Jesús Chacón <jcsombria@gmail.com>
 *
 * Copyright (C) 2013 Jesús Chacón
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.uned.dia.jcsombria.softwarelinks.rpc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;

import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParam;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamFactory;


public class JsonRpcServer {
	private Map<String, Method> methods = new HashMap<>();

	/**
	 * Default Constructor, register some test functions.
	 */
	public JsonRpcServer() {
		try {
			this.on("echoNoArgs", JsonRpcServer.class.getMethod("echo", null));
			this.on("echoOneArg", JsonRpcServer.class.getMethod("echo", RpcParam.class));
			this.on("echoTwoArgs", JsonRpcServer.class.getMethod("echo", RpcParam.class, RpcParam.class));
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Cannot register method: ");
		}
	}

	/**
	 * Echo Test
	 */
	public RpcParam echo() {
		return null;
	}
	public RpcParam echo(RpcParam message) {
		return message;
	}
	public RpcParam echo(RpcParam<String> message1, RpcParam<String> message2) {
		return RpcParamFactory.create("result", message1.get() + message2.get());
	}

	/**
	 * Send method calls in batch list
	 *
	 * @param jsonrpc - The request in JSON-RPC format
	 */
	public String parse(String jsonrpc) {
		JsonStructure request = null;
		try {
			InputStream stream = new ByteArrayInputStream(jsonrpc.getBytes("UTF-8"));
			JsonReader reader = Json.createReader(stream);
			request = reader.read();
		} catch(UnsupportedEncodingException e) {
			System.err.println(e);
		}
		switch(request.getValueType()) {
		case OBJECT:
			JsonObject call = (JsonObject) request;
			Object result = process(call);
			return result.toString();		
		case ARRAY:
			JsonArray array = (JsonArray) request;
			result = new String[array.size()];
			Iterator<JsonValue> iter = array.iterator();
			LinkedList<Object> list = new LinkedList<Object>();
			while(iter.hasNext()) {
				JsonObject object = (JsonObject)iter.next();
				list.add(object.get("result"));
			}
			result = list.toArray();
			return result.toString();
		default:
			return JsonRpcBuilder.responseWithError("", -32700, "Parse error", null).toString();
		}
	}

	/**
	 * Process a request
	 *
	 * @param jsonrpc - The request in JSON-RPC format
	 */
	public Object process(JsonObject request) {
		if(!isValidRequest(request)) {
			return JsonRpcBuilder.responseWithError("", -32700, "Parse error", null).toString();
		}
		String name = request.getString("method");
		String id = request.getString("id");
		JsonArray params = request.getJsonArray("params");
		Method method = methods.get(name);
		if(method != null) {			
			try {
				RpcParam[] arguments = null;				
				if(params != null) {
					arguments = extractParamsByPosition(params.iterator(), method);
				}
				RpcParam result = (RpcParam)method.invoke(this, (Object[])arguments);
				JsonObject aux = JsonRpcBuilder.response(result, id);
				return aux;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return JsonRpcBuilder.responseWithError(id, -32602, "Invalid params", null);
			} catch (InvocationTargetException | IllegalAccessException e) {
				return JsonRpcBuilder.responseWithError(id, -32601, "Method not found", null);
			}
		} else {
			return JsonRpcBuilder.responseWithError(id, -32601, "Method not found", null);
		}
	}

	private RpcParam[] extractParamsByPosition(Iterator<JsonValue> params, Method method) {
		ArrayList<RpcParam> extracted = new ArrayList<>();
		Class<?>[] types = method.getParameterTypes();
		for(Class<?> expected : types) {
			if(!params.hasNext()) {
				throw new IllegalArgumentException("Not enough arguments!");
			}
			JsonValue received = params.next();
			RpcParam param = JsonRpcBuilder.toJavaType(received);			
			extracted.add(param);
		}
		if(params.hasNext()) {
			throw new IllegalArgumentException("Too much arguments!");
		}
		return extracted.toArray(new RpcParam[]{}); 
	}

	private boolean isValidRequest(JsonObject request) {
		return (
			request.containsKey("jsonrpc") && request.getString("jsonrpc").equals("2.0") &&
			request.containsKey("method")
		);
	}

	/**
	 * Register a new method
	 *
	 * @param jsonrpc - The request in JSON-RPC format
	 */

	public void on(String method, Method handler) {
		methods.put(method, handler);
	}	
}