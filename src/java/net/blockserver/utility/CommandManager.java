package net.blockserver.utility;

import java.util.Map;

public class CommandManager {

    private Map<String, Command> cmds;

    public CommandManager() {
        registerDefaults();
    }

    private void registerDefaults(){
        
    }

    public boolean registerCommand(Command command){
        if(cmds.containsKey(command.getName())){
            return false;
        }
        cmds.put(command.getName(), command);
        return true;
    }

    public void runCommand(CommandIssuer issuer, String line){
        String[] args = line.split(" ");
        if(cmds.containsKey(args[0])){
            String[] fargs = {};
            for(int i = 1; i < args.length; i++){
                fargs[i - 1] = args[i];
            }
            cmds.get(args[0]).run(issuer, fargs);
        }
    }

}
