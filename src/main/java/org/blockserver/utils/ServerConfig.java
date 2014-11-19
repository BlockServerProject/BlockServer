package org.blockserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
		File simpleFile = new File(dataDir, "config.txt");
		File advancedFile = new File(dataDir, "advanced-config.properties");
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
		}
		catch(IOException e){
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
		}
		catch(NullPointerException | NumberFormatException e){
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
		properties.setProperty(PROPERTY_SERVER_NAME, "A server made using BlockServer server software");
		properties.setProperty(PROPERTY_MAX_PLAYERS, "10");
		properties.setProperty(PROPERTY_MOTD, "Welcome to %n!");
		properties.store(new FileOutputStream(file), SIMPLE_COMMENT);
	}
	private void generateAdvancedConfig(File file) throws IOException{
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_IP, "localhost");
		// TODO more properties
		properties.store(new FileOutputStream(file), ADVANCED_COMMENT);
	}
}
