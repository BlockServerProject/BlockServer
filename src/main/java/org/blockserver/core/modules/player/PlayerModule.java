/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core.modules.player;

import org.blockserver.core.Server;
import org.blockserver.core.event.MessageEventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.message.MessageInPlayerLogin;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.logging.LoggingModule;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Module that handles players.
 */
public class PlayerModule extends Module {
    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    private MessageEventListener<MessageInPlayerLogin> listener;

    public PlayerModule(Server server) {
        super(server);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        listener = new MessageEventListener<MessageInPlayerLogin>() {
            @Override
            public void onEvent(MessageHandleEvent<MessageInPlayerLogin> event) {
                Player player = new Player(getServer(), event.getMessage().getAddress()); //TODO: Fire PlayerCreatedEvent
                players.add(player);
            }
        }.register(MessageInPlayerLogin.class, getServer());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        listener.unregister(getServer());
    }

    /**
     * Attempts to find a current online player with the specified name. If
     * there is no player found this method will return null.
     *
     * @param name The name of the player to find.
     * @return The Player instance if found, null if not.
     */
    public Player getPlayer(String name) {
        return null; //TODO
    }

    /**
     * Attempts to find a current online player with the specified address.
     * If no player is found this method will return null.
     *
     * @param address The address of the player to find.
     * @return The Player instance if found, null if not.
     */
    public Player getPlayer(InetSocketAddress address) {
        for (Player player : players) {
            if (player.getAddress().equals(address))
                return player;
        }
        return null;
    }

    /**
     * Opens a new session, and adds the specified Player to the list of online
     * players.
     * <br>
     * NOTE: THIS METHOD IS FOR INTERNAL USE ONLY!
     *
     * @param player The player to be added.
     */
    public void internalOpenSession(Player player) {
        players.add(player);
        getServer().getModule(LoggingModule.class).debug("New session from " + player.getAddress().getHostString() + ":" + player.getAddress().getPort());
    }

    /**
     * Removes a Player from the list of online players.
     * <br>
     * NOTE: THIS METHOD IS FOR INTERNAL USE ONLY!
     *
     * @param player The player to be removed.
     */
    public void internalCloseSession(Player player) {
        players.remove(player);
        getServer().getModule(LoggingModule.class).debug("Session " + player.getAddress().getHostString() + ":" + player.getAddress().getPort() + " closed.");
    }

    protected final void sessionOpened(InetSocketAddress address) {
        Player player = new Player(getServer(), address);
        getServer().getModule(PlayerModule.class).internalOpenSession(player);
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }
}