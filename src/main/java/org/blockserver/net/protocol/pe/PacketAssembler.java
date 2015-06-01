package org.blockserver.net.protocol.pe;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.raknet.CustomPacket;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that assembles and disassembles split packets.
 */
public class PacketAssembler {

    public static boolean checkForSplitPackets(CustomPacket cp){
        for(CustomPacket.InternalPacket ip : cp.packets){
            if(ip.hasSplit){
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfSplitNeeded(CustomPacket packet, RakNetProtocolSession session){
        int len = packet.getLength();
        if(len >= session.getMTU()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfSplitNeeded(CustomPacket packet, CustomPacket.InternalPacket addedPacket, RakNetProtocolSession session){
        int len = packet.getLength();
        len = len + addedPacket.getLength();
        if(len >= session.getMTU()){
            return true;
        } else {
            return false;
        }
    }

    public static List<CustomPacket.InternalPacket> getSplitPackets(CustomPacket cp){
        List<CustomPacket.InternalPacket> packets = new ArrayList<>();
        for(CustomPacket.InternalPacket ip : cp.packets){
            if(ip.hasSplit){
                packets.add(ip);
            }
        }
        return packets;
    }

    public static List<AssembledPacket> assemblePackets(List<CustomPacket.InternalPacket> splitPackets){
        List<AssembledPacket> packets = new ArrayList<>();
        List<CustomPacket.InternalPacket> notGrouped = splitPackets;
        List<CustomPacket.InternalPacket> grouped = groupPackets(notGrouped);
        for(CustomPacket.InternalPacket packet : grouped){
            notGrouped.remove(packet);
        }
        while(true){
            if(grouped.isEmpty()){
                grouped = groupPackets(notGrouped);
                for(CustomPacket.InternalPacket packet1 : grouped){
                    notGrouped.remove(packet1);
                }
            } else {
                AssembledPacket packet = new AssembledPacket();
                packet.setSplitID(grouped.get(0).splitID);
                packet.setSplitCount(grouped.get(0).splitCount);
                ByteBuffer bb = ByteBuffer.allocate(findLength(grouped));
                for(int pos = 0; pos < grouped.get(0).splitCount; pos++){
                    for(CustomPacket.InternalPacket ip : grouped) {
                        if (ip.splitIndex == pos) {
                            bb.put(ip.buffer);
                            grouped.remove(ip);
                        }
                    }
                }
                packet.setBuffer(bb.array());
                packets.add(packet);
            }

            if(notGrouped.isEmpty()){
                break;
            }
        }

        return packets;
    }

    private static int findLength(List<CustomPacket.InternalPacket> grouped) {
        int len = 0;
        for(CustomPacket.InternalPacket ip : grouped){
            len = len + ip.buffer.length;
        }
        return len;
    }

    private static List<CustomPacket.InternalPacket> groupPackets(List<CustomPacket.InternalPacket> splitPackets){
        List<CustomPacket.InternalPacket> grouped = new ArrayList<>();
        while(splitPackets.iterator().hasNext()){
            CustomPacket.InternalPacket ip = splitPackets.iterator().next();
            if(grouped.get(0).splitID == ip.splitID){
                grouped.add(ip);
            }
        }
        return grouped;
    }

    public static class AssembledPacket{
        private int splitID;
        private int splitCount;
        private byte[] buffer;

        public byte[] getBuffer() {
            return buffer;
        }

        public void setBuffer(byte[] buffer) {
            this.buffer = buffer;
        }

        public int getSplitCount() {
            return splitCount;
        }

        public void setSplitCount(int splitCount) {
            this.splitCount = splitCount;
        }

        public int getSplitID() {
            return splitID;
        }

        public void setSplitID(int splitID) {
            this.splitID = splitID;
        }
    }
}
