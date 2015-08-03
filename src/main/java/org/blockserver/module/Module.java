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
package org.blockserver.module;

import org.blockserver.Server;
import org.blockserver.ui.Logger;

import java.util.Properties;


/**
 * Represents a Module.
 */
public abstract class Module{
	private Server server;
	private Logger logger;
	private String name;
	private String version;

	protected void setServer(Server server){
		this.server = server;
	}

	protected void setLogger(Logger logger){
		this.logger = logger;
	}

	protected void init(Properties properties){
		name = properties.getProperty("Name");
		version = properties.getProperty("Version");
	}

	public abstract void register();

	public final Server getServer(){
		return server;
	}

	public final Logger getLogger(){
		return logger;
	}

	public final String getName(){
		return name;
	}

	public final String getVersion(){
		return version;
	}
}
