package org.blockserver.net.bridge.raknet;

/**
 * Created by jython234 on 8/3/2015.
 */
public class NonBlockRakLibHandler extends Thread{
    private RakNetBridge bridge;

    public NonBlockRakLibHandler(RakNetBridge bridge){
        this.bridge = bridge;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            bridge.process();
        }
    }
}
