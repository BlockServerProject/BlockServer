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
import org.blockserver.net.bridge.raknet.RakNetBridge;
import org.blockserver.net.protocol.pe.PeProtocol;
import org.blockserver.ui.Log4j2ConsoleOut;
import org.blockserver.ui.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader implements Runnable{
	private Server server;
	private File modulesLocation;
	private File moduleConfig;
	private Yaml modulesYaml;
	private ArrayList<String> modulesAllowed;

	public ModuleLoader(Server server, File location){
		this.server = server;
		location.mkdirs();
		modulesLocation = location;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(){
		moduleConfig = new File("modules.yml");
		server.getLogger().info("Registering modules...");

		if(!moduleConfig.exists() || !moduleConfig.isFile()){
			server.getLogger().warning("Didn't find modules.yml. Creating file...");
			try{
				moduleConfig.createNewFile();
				modulesYaml = new Yaml();
				Map<String, Object> map = new HashMap<>();
				ArrayList<String> mods = new ArrayList<>();
				mods.add("MCPE");
				map.put("modules", mods);

				BufferedWriter writer = new BufferedWriter(new FileWriter(moduleConfig));
				writer.write(modulesYaml.dump(map));
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		modulesYaml = new Yaml();
		modulesAllowed = null;
		try{
			Map<String, Object> map = (Map<String, Object>) modulesYaml.load(new FileInputStream(moduleConfig));
			modulesAllowed = (ArrayList<String>) map.get("modules");
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		//--------REGISTER MCPE----------------------
		if(modulesAllowed.contains("MCPE")){
			PeProtocol pocket = new PeProtocol(server);
			server.getProtocols().addProtocol(pocket);
			server.getBridges().addBridge(new RakNetBridge(server.getBridges()));
		}
		//--------REGISTER MCPE END------------------
		//--------REGISTER JARS----------------------
		try{
			registerJars();
		}catch(IOException e){
			server.getLogger().error("Exception while loading JAR Modules.");
			server.getLogger().trace("IOException: "+e.getMessage());
			e.printStackTrace();
		}
		//--------REGISTER JARS END------------------

		server.getLogger().info("Modules registered!");
	}

	private void registerJars() throws IOException{
		server.getLogger().info("Searching for module JARs...");
		File[] files = modulesLocation.listFiles();
		if(files == null){
			return;
		}
		ArrayList<File> jars = new ArrayList<>();
		for(File file : files){
			if(file.getName().endsWith(".jar")){
				jars.add(file);
			}
		}
		server.getLogger().info("Found "+jars.size()+" JARs.");
		for(File jar: jars){
			loadAndRegister(jar);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadAndRegister(File jar) throws IOException{
		Properties modData =  loadModuleData(jar);
		String mainClass = modData.getProperty("mainClass");
		if(mainClass == null){
			throw new ModuleLoadException("Invalid modData.prop: does not contain property 'mainClass'.");
		}

		URLClassLoader loader = new URLClassLoader(new URL[]{jar.toURI().toURL()}, getClass().getClassLoader());
		try{
			Class clazz = Class.forName(mainClass, true, loader);
			Class<? extends Module> modClass;
			try{
				modClass = clazz.asSubclass(Module.class);
			}catch(ClassCastException e){
				throw new ModuleLoadException("Class " + mainClass + " must extend org.blockserver.Module");
			}
			Module mod = modClass.newInstance();
			mod.init(modData);
			mod.setServer(server);
			mod.setLogger(new Logger(new Log4j2ConsoleOut(modData.getProperty("Name"))));
			if(modulesAllowed.contains(mod.getName())){
				server.getLogger().info("Registering Module: " + mod.getName() + " (Version: " + mod.getVersion() + ")...");
				mod.register();
			}else{
				server.getLogger().info("Denied Module " + mod.getName() + ": Not found in modules.yml");
			}
		}catch(ClassNotFoundException e){
			throw new ModuleLoadException("Could not find main class: " + mainClass);
		}catch(ClassCastException e){
			e.printStackTrace();
			throw new ModuleLoadException("Failed to cast class: " + e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new ModuleLoadException(e.getMessage());
		}
	}

	private Properties loadModuleData(File jar) throws IOException{
		if(!jar.exists()){
			throw new ModuleLoadException("JAR Must exist.");
		}
		JarFile jarFile = new JarFile(jar);
		JarEntry entry = jarFile.getJarEntry("modData.prop");
		if(entry == null){
			throw new ModuleLoadException("Did not find modData.prop in "+jar.getName());
		}

		InputStream is = jarFile.getInputStream(entry);
		Properties prop = new Properties();
		prop.load(is);
		is.close();
		return prop;
	}
}
