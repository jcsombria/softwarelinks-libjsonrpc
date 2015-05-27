/**
 * RpcParamNull
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

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class RpcParamString implements RpcParam<String> {
	private String name;
	private String value;
	
	public RpcParamString(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public void addTo(JsonObjectBuilder object) {
		object.add(name, value);
	}
	
	@Override
	public void addTo(JsonArrayBuilder array) {
		array.add(value);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public String get() {
		return value;
	}

	@Override
	public void set(String value) {
		this.value = value;
	}
}