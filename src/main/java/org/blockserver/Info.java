/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver;

public final class Info{
	public final static String SOFTWARE_NAME = "BlockServer";
	public final static String CURRENT_STAGE = "Beta";
	public final static String CURRENT_VERSION = "v1.0.0";
	public final static String CURRENT_BUILD = "Dev-Snapshot";
	public static String VERSION_STRING(){
		StringBuilder builder = new StringBuilder();
		if(!CURRENT_STAGE.isEmpty()){
			builder.append(CURRENT_STAGE).append(' ');
		}
		if(!CURRENT_VERSION.isEmpty()){
			builder.append(CURRENT_VERSION).append(' ');
		}
		if(!CURRENT_BUILD.isEmpty()){
			builder.append(CURRENT_BUILD).append(' ');
		}
		return builder.toString().trim();
	}
}
