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

import java.util.ArrayList;

public abstract class Command{
	public abstract String getName();
	public abstract String getDescription(CommandIssuer issuer);
	public abstract String getUsage(CommandIssuer issuer);
	public void printUsage(CommandIssuer issuer){
		issuer.tell("Usage: " + getUsage(issuer));
	}

	public abstract String run(CommandIssuer issuer, ArrayList<String> args);

	@SuppressWarnings("UnusedParameters")
	public boolean canUse(CommandIssuer issuer){
		return true;
	}
}
