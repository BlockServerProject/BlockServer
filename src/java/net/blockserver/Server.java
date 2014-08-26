package net.blockserver;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.blockserver.level.Level;
import net.blockserver.network.PacketHandler;
import net.blockserver.player.BinaryPlayerDatabase;
import net.blockserver.player.Player;
import net.blockserver.player.PlayerDatabase;
import net.blockserver.scheduler.Scheduler;
import net.blockserver.utility.MinecraftVersion;
import net.blockserver.utility.ServerLogger;

/**
 * Represents an implementation of a Minecraft: Pocket Editon server.
 * @author BlockServer Team
 *
 */

public class Server {
    private static Server instance = null;
    private ConsoleCommandHandler cmdHandler = null;

    private ServerLogger logger = new ServerLogger();
    private Scheduler scheduler;
    private PacketHandler packetHandler;

    private Map<String, Player> players;

    private MinecraftVersion MCVERSION;
    private String VERSION = "0.1 - DEV";
    private String serverName;
    private String serverip;

    private boolean serverRunning;

    private long startTime;
    private long serverID;

    private int serverPort;
    private int maxPlayers;

    private Map<String, Level> levels;

    private String defaultLevel;

    private File serverDir;
    
    /**
     * The Server's player database.
     */
    public PlayerDatabase playerDb;
    private File worldsDir;

    /**
     * Get an instance of the server, (Broken).
     * @return A Server object of the server.
     */
    public static synchronized Server getInstance(){
        return instance;
    }

    /**
     * Sets the instance of this class, (Broken).
     * @param i The instance to set.
     */
    public static void setInstance(Server i){
        instance = i;
    }

    /**
     * Access the server scheduler.
     * @return Returns the server's scheduler.
     */
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    /**
     * Access the server's logger.
     * @return The server's logger.
     */
    public ServerLogger getLogger() {
        return this.logger;
    }

    /**
     * Get the Minecraft version that the server is using.
     * @return An Enum of the Minecraft version.
     */
    public MinecraftVersion getMinecraftVersion() {
        return MCVERSION;
    }

    /**
     * Get the server's listening IP.
     * @return The server's listening IP.
     */
    public String getServerIP(){
        return serverip;
    }

    /**
     * Get the server's name (shows up in the world list).
     * @return The server name (Includes header (MCCPP;MINECON;).
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Get the server's version (BlockServer version)
     * @return A String of the server version.
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * Get the server's port it is listening on.
     * @return The server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Get the maximum players the server can handle.
     * @return The server's maximum players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the server's randomly generated serverID.
     * @return The server's serverID.
     */
    public long getServerID() {
        return serverID;
    }

    /**
     * Get the server's start time (in milliseconds).
     * @return The server's start time.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Gets the boolean if the server is running
     * @return The server status.
     */
    public boolean isRunning() {
        return serverRunning;
    }

    /**
     * Get a player from the server's player hashmap.
     * @param ip The IP of the player.
     * @param port The Port of the player.
     * @return The Player object of the player.
     */
    public Player getPlayer(String ip, int port) {
        return players.get(ip + Integer.toString(port));
    }

    /**
     * Adds a player to the player hashmap.
     * @param player The Player object to be added.
     */
    public void addPlayer(Player player) {
        players.put(player.getIP() + Integer.toString(player.getPort()), player);
    }

    /**
     * Constructor for the Server implementation.
     * @param name The Server name (shows up in the world list).
     * @param ip The Server binding IP.
     * @param port The Server binding port.
     * @param players The maximum players.
     * @param version The Minecraft Version that this server will handle.
     * @param defaultLevel The folder name of the default world to load.
     * @param dbType The Player database type.
     * @throws Exception Throws an Exception if the server can't find the server's CWD.
     */
    public Server(String name, String ip, int port, int players, MinecraftVersion version, String defaultLevel, Class<? extends PlayerDatabase> dbType) throws  Exception{
        Thread.currentThread().setName("ServerThread");

        String path = new File(".").getCanonicalPath();
        serverDir = new File(path);
        serverDir.mkdirs(); // redundant line

        startTime = System.currentTimeMillis();
        serverip = ip;
        serverPort = port;
        serverName = "MCCPP;Demo;" + name;
        maxPlayers = players;
        MCVERSION = version;
        serverID = new Random().nextLong();

        this.players = new HashMap<String, Player>(players);

        /*
        worldsDir = new File(serverDir, "worlds/");
        int cnt = worldsDir.list(new RootDirectoryFilter(worldsDir)).length;
        levels = new HashMap<String, Level>(cnt);
        boolean success = loadLevel(defaultLevel, true);
        if(!success){
//            throw new RuntimeException("Unable to generate default level");
        }
        */
        logger.warning("Note: this version currently does not support world loading."); //TODO: Change this when we can load levels :)
        logger.warning("Note: The server only supports BinaryPlayerDatabase files, .bsf");

        scheduler = new Scheduler();// Minecraft default Ticks Per Seconds(20)
        packetHandler = new PacketHandler(this);
        cmdHandler = new ConsoleCommandHandler(this);
        playerDb = new BinaryPlayerDatabase(this);
    }

    private class RootDirectoryFilter implements FilenameFilter{
        private File dir;
        public RootDirectoryFilter(File dir){
            this.dir = dir;
        }
        @Override
        public boolean accept(File dir, String name){
            if(dir.equals(this.dir)){
                File d = new File(dir, name);
                return d.isDirectory();
            }
            return false;
        }
    }

    /**
     * Main server method, all the handlers are started up here.
     */
    public void run() {
        serverRunning = true;
        try {
            logger.info("Starting server on: *:" + serverPort + ", implementing " + MinecraftVersion.versionToString(MCVERSION));
            logger.info("This is version " + VERSION);
            scheduler.Start();
            logger.info("Server Scheduler Started...");
            playerDb.init();
            packetHandler.Start();
            cmdHandler.Start();
        } catch (SocketException e) {
            logger.fatal("COULD NOT BIND TO PORT - Maybe another server is running on " + serverPort + "?");
            logger.fatal("Shutting down server due to error.");
            System.exit(1);
        } catch (IOException e) {
            int time = (int) (System.currentTimeMillis() - this.startTime);
            logger.warning("IOException at: " + time + " ms");
        } catch (Exception e) {
            int time = (int) (System.currentTimeMillis() - this.startTime);
            logger.warning("Exception at: " + time + " ms");
            logger.warning(e.getMessage());
        }

    }

    /**
     * Send a packet to any address.
     * @param buffer The buffer of the packet.
     * @param ip The client's IP address.
     * @param port The client's port.
     */
    public void sendPacket(byte[] buffer, String ip, int port) {
        try {
            this.packetHandler.sendPacket(buffer, ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the server.
     * @throws Exception Throws an Exception in case one of the handlers can not be stopped.
     */
    public void stop() throws Exception {
        serverRunning = false;
        scheduler.Stop();
        packetHandler.Stop();
        cmdHandler.Stop();
    }

    /**
     * Get the server's default level.
     * @return The server's default level (As a Level object).
     */
	public Level getDefaultLevel() {
		return levels.get(defaultLevel);
	}

	/**
	 * Get a Level object by its name.
	 * @param name The name of the folder of this level.
	 * @param load Whether to load this world. 
	 * @param generate Whether to generate this world.
	 * @return A Level object of this world.
	 */
	public Level getLevel(String name, boolean load, boolean generate){
	    if(levels.containsKey(name)){
	        return levels.get(name);
	    }
	    if(load){
	        boolean success = loadLevel(name, generate);
	        if(success){
	            return levels.get(name);
	        }
	    }
	    return null;
	}
	
	/**
	 * Load a level by its name.
	 * @param name The name of the folder of the level.
	 * @param generate Whether to generate this level.
	 * @return If the the generation succeeded, or if the world is a valid level.
	 */
    public boolean loadLevel(String name, boolean generate) {
        File dir = new File(getWorldsDir(), name);
        if(dir.isDirectory()){
            // TODO Auto-generated method stub
            return false;
        }
        else if(generate){
            return generateLevel(name);
        }
        else{
            return false;
        }
    }

    /**
     * Generate a level by name.
     * @param name The name of the level.
     * @return If the level had been generated correctly.
     */
	public boolean generateLevel(String name) {
        // TODO Auto-generated method stub
        return false;
    }

	/**
	 * Get the worlds folder of the server.
	 * @return A File object of the worlds folder.
	 */
    public File getWorldsDir(){
	    return worldsDir;
	}

    /**
     * Get the PlayerDatabase of the server.
     * @return A PlayerDatabase object of what the server is using.
     */
    public PlayerDatabase getPlayerDatabase() {
        return playerDb;
    }

    /**
     * Get the server's folder (usually the CWD).
     * @return A File object of the server's folder.
     */
    public File getServerFolder(){
        return serverDir;
    }

}
