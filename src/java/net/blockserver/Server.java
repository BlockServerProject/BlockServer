package net.blockserver;

import net.blockserver.level.Level;
import net.blockserver.network.PacketHandler;
import net.blockserver.scheduler.Scheduler;
import net.blockserver.utility.MinecraftVersion;
import net.blockserver.utility.ServerLogger;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    public Player getPlayer(String ip)
    {
        return this.players.get(ip);
    }

    public void addPlayer(Player player)
    {
        this.players.put(player.getIP(), player);
    }

    public Server(String name, String ip, int port, int players, MinecraftVersion version) throws  Exception{
        Thread.currentThread().setName("ServerThread");
        instance = this;

        this.startTime = System.currentTimeMillis();
        this.serverip = ip;
        this.serverPort = port;
        this.serverName = "MCCPP;Demo;" + name;
        this.maxPlayers = players;
        this.MCVERSION = version;
        this.serverID = new Random().nextLong();

        this.players = new HashMap<String, Player>(this.maxPlayers);

        this.scheduler = new Scheduler();// Minecraft Deafult Ticks Per Seconds(20)
        this.packetHandler = new PacketHandler(this);
        this.cmdHandler = new ConsoleCommandHandler(this);
}

    public void run() {
        this.serverRunning = true;
        try {
            this.logger.info("Starting server on: *:" + serverPort + ", implementing " + MinecraftVersion.versionToString(MCVERSION));
            this.logger.info("This is version " + VERSION);
            this.scheduler.Start();
            this.logger.info("Server Scheduler Started...");
            this.packetHandler.Start();
            this.cmdHandler.Start();
        } catch (SocketException e) {
            this.logger.fatal("COULD NOT BIND TO PORT - Maybe another server is running on " + serverPort + "?");
            this.logger.fatal("Shutting down server due to error.");
            System.exit(1);
        } catch (IOException e) {
            int time = (int) (System.currentTimeMillis() - this.startTime);
            this.logger.warning("IOException at: " + time + " ms");
        } catch (Exception e) {
            int time = (int) (System.currentTimeMillis() - this.startTime);
            this.logger.warning("Exception at: " + time + " ms");
            this.logger.warning(e.getMessage());
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

    public void Stop() throws Exception {
        this.serverRunning = false;
        this.scheduler.Stop();
        this.packetHandler.Stop();
        this.cmdHandler.Stop();
    }

	public Level getDefaultLevel() {
		return this.defaultLevel;
	}

}
