/**
 * RpcParamFactory
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
package es.uned.dia.jcsombria.softwarelinks.rpc.param;

public class RpcParamFactory {

	public static RpcParam createNull(String name) {
		return new RpcParamNull(name);
	}

	public static RpcParam<Integer> create(String name, int value) {
		return new RpcParamInteger(name, value);
	}
	
	public static RpcParam<Double> create(String name, double value) {
		return new RpcParamDouble(name, value);
	}
	
	public static RpcParam<Boolean> create(String name, boolean value) {
		return new RpcParamBoolean(name, value);
	}
	
	public static RpcParam<String> create(String name, String value) {
		return (RpcParam<String>)new RpcParamString(name, value);
	}
	
	public static RpcParam<Double[]> create(String name, double[] value) {
		return new RpcParamDoubleArray(name, value);
	}

	
	public static RpcParam create(String name, Object value) {
		if(value == null) {
			return createNull(name);
		}
		Class<?> type = value.getClass();
		System.out.println(type.getName());
		if(type == int.class || type == Integer.class) {
			System.out.println("Integer");
			return create(name, (int)value);
		} else if(type == double.class || type == Double.class) {
			System.out.println("Double");
			return create(name, (double)value);
		} else if(type == String.class) {
			System.out.println("String");
			return create(name, (String)value);
		} else if(type == boolean.class || type == Boolean.class) {
			System.out.println("Boolean");
			return create(name, (boolean)value);
		} else if(type == double[].class) {
			System.out.println("double[]");
			return create(name, (double[])value);
		} else if(type == Double[].class) {
			System.out.println("double[]");
			return create(name, (Double[])value);
		} else {
			return createNull(name);
		}
	}
}