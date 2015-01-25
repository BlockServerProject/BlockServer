package org.blockserver.level;

import org.blockserver.Server;

/**
 * Created by jython234 on 1/24/2015.
 */
public class LevelManager {
    private ILevel levelImpl;
    private Server server;

    public LevelManager(Server server){
        this.server = server;
    }

    public ILevel getLevelImplemenation(){
        return levelImpl;
    }

    public void setLevelImpl(ILevel level){
        this.levelImpl = level;
    }
}
