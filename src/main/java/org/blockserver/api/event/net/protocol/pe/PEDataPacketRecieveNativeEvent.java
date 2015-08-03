package org.blockserver.api.event.net.protocol.pe;

import org.blockserver.api.NativeEvent;

/**
 * Fired when a new data packet is recieved.
 * NOTE: If you cancel this event, the server WILL NOT run the handling code for this packet.
 */
public class PEDataPacketRecieveNativeEvent extends NativeEvent{

}
