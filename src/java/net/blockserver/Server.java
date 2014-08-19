package net.blockserver;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.blockserver.level.Level;
import net.blockserver.network.PacketHandler;
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
    private Level defaultLevel;

    public PlayerDatabase playerDb;

    public static synchronized Server getInstance(){
        return instance;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public ServerLogger getLogger() {
        return this.logger;
    }

    public MinecraftVersion getMinecraftVersion() {
        return this.MCVERSION;
    }

    public String getServerIP(){
        return this.serverip;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getVersion() {
        return this.VERSION;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public long getServerID() {
        return this.serverID;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean isRunning() {
        return this.serverRunning;
    }

    public Player getPlayer(String ip, int port)
    {
        return this.players.get(ip + Integer.toString(port));
    }

    public void addPlayer(Player player)
    {
        this.players.put(player.getIP() + Integer.toString(player.getPort()), player);
    }

    public Server(String name, String ip, int port, int players, MinecraftVersion version, Class<? extends PlayerDatabase> dbType) throws  Exception{
        Thread.currentThread().setName("ServerThread");
        instance = this;

        startTime = System.currentTimeMillis();
        serverip = ip;
        serverPort = port;
        serverName = "MCCPP;Demo;" + name;
        maxPlayers = players;
        MCVERSION = version;
        serverID = new Random().nextLong();

        this.players = new HashMap<String, Player>(players);

        scheduler = new Scheduler();// Minecraft default Ticks Per Seconds(20)
        packetHandler = new PacketHandler(this);
        cmdHandler = new ConsoleCommandHandler(this);
        playerDb = dbType.newInstance();
    }

    public void run() {
        this.serverRunning = true;
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
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        this.serverRunning = false;
        this.scheduler.Stop();
        this.packetHandler.Stop();
        this.cmdHandler.Stop();
    }

	public Level getDefaultLevel() {
		return this.defaultLevel;
	}

    public PlayerDatabase getPlayerDatabase() {
        return playerDb;
    }

}
