package org.blockserver.module;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.blockserver.Server;
import org.blockserver.net.bridge.UDPBridge;
import org.blockserver.net.protocol.pe.PeProtocol;
import org.blockserver.net.protocol.pe.sub.v20.PeSubprotocolV20;
import org.blockserver.ui.Log4j2ConsoleOut;
import org.blockserver.ui.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by jython234 on 2/6/2015.
 */
public class ModuleLoader implements Runnable{
    private Server server;
    private File modulesLocation;
    private File moduleConfig;
    private Yaml modulesYaml;
    private ArrayList<String> modulesAllowed;

    public ModuleLoader(Server server, File location){
        this.server = server;
        modulesLocation = location;
    }

    public void run(){
        moduleConfig = new File("modules.yml");
        server.getLogger().info("Registering modules...");

        if(!moduleConfig.exists() || !moduleConfig.isFile()){
            server.getLogger().warning("Didn't find modules.yml. Creating file...");
            try {
                moduleConfig.createNewFile();
                modulesYaml = new Yaml();
                Map<String, Object> map = new HashMap<String, Object>();
                ArrayList<String> mods = new ArrayList<String>();
                mods.add("MCPE");
                map.put("modules", mods);

                BufferedWriter writer = new BufferedWriter(new FileWriter(moduleConfig));

                writer.write(modulesYaml.dump(map));

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        modulesYaml = new Yaml();
        modulesAllowed = null;
        try {
            Map<String, Object> map = (Map<String, Object>) modulesYaml.load(new FileInputStream(moduleConfig));
            modulesAllowed = (ArrayList<String>) map.get("modules");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //--------REGISTER MCPE----------------------
        if(modulesAllowed.contains("MCPE")) {
            PeProtocol pocket = new PeProtocol(server);
            server.getProtocols().addProtocol(pocket);
            server.getBridges().addBridge(new UDPBridge(server.getBridges()));
            pocket.getSubprotocols().registerSubprotocol(new PeSubprotocolV20(server));
        }
        //--------REGISTER MCPE END------------------
        //--------REGISTER JARS----------------------
        try {
            registerJars();
        } catch (IOException e) {
            server.getLogger().error("Exception while loading JAR Modules.");
            server.getLogger().trace("IOException: "+e.getMessage());
            e.printStackTrace();
        }
        //--------REGISTER JARS END------------------

        server.getLogger().info("Modules registered!");
    }

    private void registerJars() throws IOException {
        server.getLogger().info("Searching for module JARs...");
        File[] files = modulesLocation.listFiles();
        ArrayList<File> jars = new ArrayList();
        for(File file: files){
            if(file.getName().endsWith(".jar")){
                jars.add(file);
            }
        }
        server.getLogger().info("Found "+jars.size()+" JARs.");
        for(File jar: jars) {
            loadAndRegister(jar);
        }
    }

    private void loadAndRegister(File jar) throws IOException {
        Properties modData =  loadModuleData(jar);
        String mainClass = modData.getProperty("mainClass");
        if(mainClass == null){
            throw new ModuleLoadException("Invalid modData.prop: does not contain property 'mainClass'.");
        }

        URLClassLoader loader = new URLClassLoader(new URL[] {jar.toURI().toURL()}, getClass().getClassLoader());
        try {
            Class clazz = Class.forName(mainClass, true, loader);
            Class<? extends Module> modClass = clazz.asSubclass(Module.class);
            Module mod = modClass.newInstance();
            mod.init(modData);
            mod.setServer(server);
            mod.setLogger(new Logger(new Log4j2ConsoleOut(modData.getProperty("Name"))));
            if(modulesAllowed.contains(mod.getName())) {
                server.getLogger().info("Registering Module: " + mod.getName() + " (Version: " + mod.getVersion() + ")...");
                mod.register();
            } else {
                server.getLogger().info("Denied Module "+mod.getName()+": Not found in modules.yml");
            }
        } catch (ClassNotFoundException e) {
            throw new ModuleLoadException("Could not find main class: " + mainClass);
        } catch(ClassCastException e){
            e.printStackTrace();
            throw new ModuleLoadException("Failed to cast class: "+e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            throw new ModuleLoadException(e.getMessage());
        }
    }

    private Properties loadModuleData(File jar) throws IOException {
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
