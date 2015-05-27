/**
 * JSON-RPC Builder
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

import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParam;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamBoolean;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamDouble;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamDoubleArray;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamInteger;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamNull;
import es.uned.dia.jcsombria.softwarelinks.rpc.param.RpcParamString;


/**
 * JSON-RPC Helper class 
 */
public class JsonRpcBuilder {
	/**
	 * Build a JSON-RPC request object
	 *
	 * @param method - The method to call
	 * @param params - The params either by position (array) or 
	 * 								  by name (object).
	 * @param id - The id of the call
	 */
	public static JsonObject request(String method, RpcParam[] params, String id) {		
		JsonObjectBuilder request = Json.createObjectBuilder()
			.add("jsonrpc", "2.0")
			.add("method", method);		
		if(params != null) {
			JsonArrayBuilder paramsArray = Json.createArrayBuilder();
			for(RpcParam item : params) {
				item.addTo(paramsArray);
			}
			request.add("params", paramsArray);
		}
		if(id!=null) request.add("id", id);
		return request.build();
	}

	/**
	 * Build a JSON-RPC response object
	 *
	 * @param result - The result to return
	 * @param id - The id of the call
	 */
	public static JsonObject response(RpcParam result, String id) {
		if(result == null) {
			return buildResponse(new RpcParamNull("result"), id);
		}
		return buildResponse(result, id);
	}
	
	public static JsonObject response(int result, String id) {
		RpcParam param = (RpcParam)new RpcParamInteger("result", result);
		return buildResponse(param, id);
	}

	private static JsonObject buildResponse(RpcParam result, String id) {
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("jsonrpc", "2.0");
		result.addTo(response);
		response.add("id", id);
 		return response.build();
	}
	
	public static JsonObject response(double result, String id) {
		RpcParam param = (RpcParam)new RpcParamDouble("result", result);
		return buildResponse(param, id);
	}
	
	public static JsonObject response(boolean result, String id) {
		RpcParam param = (RpcParam)new RpcParamBoolean("result", result);
		return buildResponse(param, id);
	}
	
	public static JsonObject response(String result, String id) {
		RpcParam param = (RpcParam)new RpcParamString("result", result);
		return buildResponse(param, id);
	}

	public static JsonObject response(double[] result, String id) {
		RpcParam param = (RpcParam)new RpcParamDoubleArray("result", result);
		return buildResponse(param, id);
	}

	/**
	 * Build a JSON-RPC error response object
	 *
	 * @param error - The error object
	 * @param id - The id of the call
	 */
	public static JsonObject responseWithError(String id, JsonObject error) {
		JsonObject model = Json.createObjectBuilder()
		   .add("jsonrpc", "2.0")
		   .add("error", error)
		   .add("id", id)
		   .build();
 		return model;
	}

	/**
	 * Build a JSON-RPC error response object
	 *
	 * @param id - The id of the call
	 * @param code - The error code
	 * @param message - The error message
	 * @param data - The error data
	 */
	public static JsonObject responseWithError(String id, int code, String message, Object data) {
		JsonObjectBuilder error = Json.createObjectBuilder()
				.add("code", code)
				.add("message", message);
		if(data != null) {
			error.add("data", data.toString());
		}						
		JsonObject model = Json.createObjectBuilder()
				   .add("jsonrpc", "2.0")
				   .add("error", error.build())
				   .add("id", id)
				   .build();
 		return model;
	}
	
	/**
	 * Build a JSON-RPC error object
	 *
	 * @param code - The result to return
	 * @param message - The error description message 
	 * @param data - Additional data
	 */
	public static JsonObject error(int code, String message, String data) {
		JsonObject model = Json.createObjectBuilder()
		   .add("jsonrpc", "2.0")
		   .add("message", message)
		   .add("data", data)
		   .build();
 		return model;
	}
	
	protected static RpcParam toJavaType(JsonValue value) {
		if(value == null) {
			return null;
		}
		switch(value.getValueType()) {
		case OBJECT:
			return null;
		case ARRAY:
			JsonArray array = ((JsonArray)value); 
			Iterator<JsonValue> iter = array.iterator();
			double[] result = new double[array.size()];
			for(int i=0; iter.hasNext(); i++) {
				JsonNumber next = ((JsonNumber)iter.next());
				result[i] = next.doubleValue();
			}
			return (RpcParam)new RpcParamDoubleArray("result", result);
		case STRING:
			return new RpcParamString("result", ((JsonString)value).getString());
		case NUMBER:
			JsonNumber number = ((JsonNumber)value);
			if(number.isIntegral()) {
				return (RpcParam)new RpcParamInteger("result", number.intValue());				
			} else {
				return (RpcParam)new RpcParamDouble("result", number.doubleValue());				
			}
		case FALSE:
			return (RpcParam)new RpcParamBoolean("result", false);
		case TRUE:
			return (RpcParam)new RpcParamBoolean("result", true);
		case NULL:
			return (RpcParam)new RpcParamNull("result");
		}
		throw new IllegalArgumentException("Incorrect types");
	}

//	public static Object getParamAsType(JsonValue value, Class<?> expected) {
//		switch(value.getValueType()) {
//		case OBJECT:
//			break;
//		case ARRAY:
//			break;
//		case STRING:
//			String data = ((JsonString)value).getString();
//			return expected.cast(data);
//		case NUMBER:
//			JsonNumber number = ((JsonNumber)value);
//			expected.cast(number.doubleValue());
//			if(expected == Integer.class) {
//				return number.intValue();
//			} else if(expected == Long.class) {
//				return number.longValue();
//			} else if(expected == Double.class) {
//				return number.doubleValue();
//			} else {
//				return expected.cast(number);
//			}
//		case FALSE:
//			return expected.cast(false);
//		case TRUE:
//			return expected.cast(true);
//		case NULL:
//			return expected.cast(null);
//		}
//		throw new IllegalArgumentException("Incorrect types");
//	}
}