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
package org.blockserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("all")
public class ServerConfig{
	public final static String PROPERTY_PORT = "port";
	public final static String PROPERTY_SERVER_NAME = "server-name";
	public final static String PROPERTY_MAX_PLAYERS = "max-players";
	public final static String PROPERTY_MOTD = "motd";
	public final static String PROPERTY_IP = "ip";
	public final static String SIMPLE_COMMENT = "Edit this file to change BlockServer normal settings. Selectors for MOTD: @n (server name), @p (player name)";
	public final static String ADVANCED_COMMENT = "Advanced BlockServer settings - only edit this file if you know what you are doing!";

	private Properties simple, advanced;
	public ServerConfig(File dataDir){
		File simpleFile = new File(dataDir, "server.properties");
		File advancedFile = new File(dataDir, "advanced-server.properties");
		try{
			if(!simpleFile.isFile()){
				generateSimpleConfig(simpleFile);
			}
			if(!advancedFile.isFile()){
				generateAdvancedConfig(advancedFile);
			}
			simple = new Properties();
			simple.load(new FileInputStream(simpleFile));
			advanced = new Properties();
			advanced.load(new FileInputStream(advancedFile));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public boolean getBooleanProperty(String name, boolean defaultValue){
		String value = getStringProperty(name);
		if(value.equalsIgnoreCase("true")){
			return true;
		}
		if(value.equalsIgnoreCase("false")){
			return false;
		}
		return defaultValue;
	}
	public int getIntProperty(String name, int defaultValue){
		try{
			return Integer.parseInt(getStringProperty(name));
		}catch(NullPointerException | NumberFormatException e){
			return defaultValue;
		}
	}
	public File getFileProperty(String name){
		return new File(getStringProperty(name));
	}
	public String getStringProperty(String name){
		if(simple.containsKey(name)){
			return simple.getProperty(name);
		}
		return advanced.getProperty(name);
	}

	private void generateSimpleConfig(File file) throws IOException{
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_PORT, "19132");
		properties.setProperty(PROPERTY_SERVER_NAME, "A MCPE Server made with BlockServer!");
		properties.setProperty(PROPERTY_MAX_PLAYERS, "10");
		properties.setProperty(PROPERTY_MOTD, "Welcome to %n!");
		properties.store(new FileOutputStream(file), SIMPLE_COMMENT);
	}
	private void generateAdvancedConfig(File file) throws IOException{
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_IP, "0.0.0.0");
		// TODO more properties
		properties.store(new FileOutputStream(file), ADVANCED_COMMENT);
	}
}
