package net.blockserver.network.raknet;

import net.blockserver.network.RaknetsID;
import net.blockserver.network.minecraft.BaseDataPacket;
import net.blockserver.utility.Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AcknowledgePacket implements BaseDataPacket
{
    public byte[] buffer;
    public int[] sequenceNumbers;
    public abstract byte getPID(); // Packet ID


    public void encode()
    {
        ByteBuffer bb = ByteBuffer.allocate(512);
        bb.position(3);
        Arrays.sort(this.sequenceNumbers);
        int count = this.sequenceNumbers.length;
        int records = 0;

        if(count > 0){
            int pointer = 1;
            int start = this.sequenceNumbers[0];
            int last = this.sequenceNumbers[0];

            while(pointer < count){
                int current = this.sequenceNumbers[pointer++];
                int diff = current - last;
                if(diff == 1){
                    last = current;
                }else if(diff > 1){ //Forget about duplicated packets (bad queues?)
                    if(start == last){
                        bb.put((byte)0x01);
                        bb.put(Utils.putLTriad(start));
                        start = last = current;
                    }else{
                        bb.put((byte)0x00);
                        bb.put(Utils.putLTriad(start));
                        bb.put(Utils.putLTriad(last));
                        start = last = current;
                    }
                    ++records;
                }
            }

            if(start == last){
                bb.put((byte)0x01);
                bb.put(Utils.putLTriad(start));
            }else{
                bb.put((byte)0x00);
                bb.put(Utils.putLTriad(start));
                bb.put(Utils.putLTriad(last));
            }
            ++records;
        }

        int length = bb.position();
        bb.position(0);
        bb.put(this.getPID());
        bb.putShort((short)records);
        this.buffer = Arrays.copyOf(bb.array(), length);
    }

    public void decode()
    {
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        byte pid = bb.get(); // Packet ID

        int count = bb.getShort();
        List<Integer> packets = new ArrayList<>();
        for(int i = 0; i < count && bb.position() < bb.capacity(); ++i)
        {
            byte[] tmp = new byte[6];
            if(bb.get() == 0x00){

                bb.get(tmp);
                int start = Utils.getLTriad(tmp, 0);
                int end = Utils.getLTriad(tmp, 3);
                if((end - start) > 4096){
                    end = start + 4096;
                }
                for(int c = start; c <= end; ++c){
                    packets.add(c);
                }
            }else {
                bb.get(tmp, 0, 3);
                packets.add(Utils.getLTriad(tmp, 0));
            }
        }

        this.sequenceNumbers = new int[packets.size()];
        for (int i = 0; i < this.sequenceNumbers.length; i++)
        {
            this.sequenceNumbers[i] = packets.get(i);
        }
    }
}
