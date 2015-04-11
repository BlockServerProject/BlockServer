package org.blockserver.api;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Base class for all events, Module events must extend this too.
 */
public abstract class Event {
    private ArrayList<API.Argument> args = new ArrayList<>();
    private boolean isCanceled = false;

    public Event(API.Argument... args){
        Collections.addAll(this.args, args);
    }

    public Event() { }

    public void addArgument(API.Argument arg, int position){
        args.add(position, arg);
    }

    public void removeArgument(int position){
        args.remove(position);
    }

    public void removeArgument(API.Argument arg){
        args.remove(arg);
    }

    public void setCanceled(boolean canceled){
        isCanceled = canceled;
    }

    public boolean isCanceled(){
        return isCanceled;
    }

    public final ArrayList<API.Argument> getArguments(){
        return args;
    }
}
