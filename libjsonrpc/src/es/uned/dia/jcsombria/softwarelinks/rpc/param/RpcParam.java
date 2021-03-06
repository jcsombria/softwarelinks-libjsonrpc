/**
 * RpcParam
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

public interface RpcParam<T> {
	public void addTo(JsonObjectBuilder object);
	public void addTo(JsonArrayBuilder object);
	public String getName();
	public void setName(String name);
	public T get();
	public void set(T value);
}
