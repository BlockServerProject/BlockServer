package org.blockserver.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigAgent {
	
	public static Properties generateConfig(){
		Properties prop = new Properties();
		prop.setProperty("Name", "Minecraft: PE Server");
		prop.setProperty("Port", "19132");
		prop.setProperty("MaxPlayers", "5");
		prop.setProperty("MOTD", "Welcome to the server!");
		
		return prop;
	}
	
	public static void saveConfig(Properties prop, File file){
		if(! file.exists()){
			try {
				file.createNewFile();
				prop.store(new FileOutputStream(file), "BlockServer, config file.");
			} catch (IOException e) {
				throw new RuntimeException(e.getCause().getMessage());
			}
		}
		else{
			try {
				prop.store(new FileOutputStream(file), "BlockServer, config file.");
			} catch (IOException e) {
				throw new RuntimeException(e.getCause().getMessage());
			}
		}
		
	}
	
	@SuppressWarnings("finally")
	public static Properties loadConfig(File file){
		if(! file.exists()){
			throw new RuntimeException("The file given does not exist!");
		}
		else{
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream(file));
			} catch (IOException e) {
				throw new RuntimeException(e.getCause().getMessage());
			} finally {
				return prop;
			}
		}
	}

}
