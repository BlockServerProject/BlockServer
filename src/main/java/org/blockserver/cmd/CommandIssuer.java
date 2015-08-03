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
package org.blockserver.cmd;

import org.blockserver.Server;
import org.blockserver.ui.Logger;

public interface CommandIssuer{
	public void tell(String msg, Object... args);
	public static class ConsoleIssuer implements CommandIssuer{
		@SuppressWarnings("UnusedDeclaration")
		private Server server;
		private Logger logger;
		public ConsoleIssuer(Server server){
			this.server = server;
			logger = server.getLogger();
		}
		@Override
		public void tell(String msg, Object... args){
			if(args.length > 0){
				msg = String.format(msg, args);
			}
			logger.info(msg);
		}
	}
}
