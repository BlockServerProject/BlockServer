package org.blockserver.module;

import org.blockserver.Server;
import org.blockserver.net.bridge.UDPBridge;
import org.blockserver.net.protocol.pe.PeProtocol;
import org.blockserver.net.protocol.pe.sub.v20.PeSubprotocolV20;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by jython234 on 2/6/2015.
 */
public class ModuleLoader implements Runnable{
    private Server server;
    private File modulesLocation;

    public ModuleLoader(Server server, File location){
        this.server = server;
        modulesLocation = location;
    }

    public void run(){
        server.getLogger().info("Registering modules...");

        //--------REGISTER MCPE----------------------
        PeProtocol pocket = new PeProtocol(server);
        server.getProtocols().addProtocol(pocket);
        server.getBridges().addBridge(new UDPBridge(server.getBridges()));
        pocket.getSubprotocols().registerSubprotocol(new PeSubprotocolV20(server));
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
            Class clazz = Class.forName(mainClass);
            Module mod = (Module) clazz.cast(Module.class);
            mod.init(modData);
            mod.setServer(server);
            server.getLogger().info("Registering Module: "+mod.getName()+" (Version: "+mod.getVersion()+")...");
            mod.register();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
