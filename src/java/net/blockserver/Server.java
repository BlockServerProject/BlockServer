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

    public PlayerDatabase playerDb;
    private File worldsDir;

    public static synchronized Server getInstance(){
        return instance;
    }

    public static void setInstance(Server i){
        instance = i;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public ServerLogger getLogger() {
        return this.logger;
    }

    public MinecraftVersion getMinecraftVersion() {
        return MCVERSION;
    }

    public String getServerIP(){
        return serverip;
    }

    public String getServerName() {
        return serverName;
    }

    public String getVersion() {
        return VERSION;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public long getServerID() {
        return serverID;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isRunning() {
        return serverRunning;
    }

    public Player getPlayer(String ip, int port)
    {
        return players.get(ip + Integer.toString(port));
    }

    public void addPlayer(Player player)
    {
        players.put(player.getIP() + Integer.toString(player.getPort()), player);
    }

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

    public void sendPacket(byte[] buffer, String ip, int port)
    {
        try {
            this.packetHandler.sendPacket(buffer, ip, port);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        serverRunning = false;
        scheduler.Stop();
        packetHandler.Stop();
        cmdHandler.Stop();
    }

	public Level getDefaultLevel() {
		return levels.get(defaultLevel);
	}

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

	public boolean generateLevel(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public File getWorldsDir(){
	    return worldsDir;
	}

    public PlayerDatabase getPlayerDatabase() {
        return playerDb;
    }

    public File getServerFolder(){
        return serverDir;
    }

}
