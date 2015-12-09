package org.blockserver.core.modules.network.v2.TCP;


import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.modules.network.RawPacket;
import org.blockserver.core.modules.network.v2.NetworkProvider;
import org.blockserver.core.utilities.NetworkUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Written by Exerosis!
 */
public class TCPNetworkProvider extends NetworkProvider {
    @Getter private ServerSocketChannel socketSever;
    @Getter private final Map<SocketAddress, SocketChannel> channels = new HashMap<>();

    public TCPNetworkProvider(Server server, int port) {
        super(server);
        try {
            socketSever = ServerSocketChannel.open();
            socketSever.bind(new InetSocketAddress(NetworkUtil.getLocalHost(), port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getExecutorService().execute(() -> {
            while (socketSever.isOpen()) {
                try {
                    SocketChannel socket = socketSever.accept();
                    channels.put(socket.getRemoteAddress(), socket);
                    //blah blah listen to socket to get receive bytes

                    //on byte received
                    queuePackets(Collections.singletonList(new RawPacket(new byte[]{}, socket.getRemoteAddress())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void sendPackets(Collection<RawPacket> packets) {
        for (RawPacket message : packets) {
            try {
                channels.get(message.getAddress()).write(ByteBuffer.wrap(message.getBuffer()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}