package net.blockserver.cmd;

import java.util.Locale;
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
            if(args[0].equals("help")){
                // TODO
            }
            else{
                Command cmd = cmds.get(args[0]);
                if(cmd instanceof Command){
                    String result = cmds.get(args[0]).run(issuer, fargs);
                    if(result instanceof String){
                        issuer.sendMessage(result);
                    }
                }
                else{
                    issuer.sendMessage(String.format(Locale.US, "Command /%s doesn't exist!", args[0]));
                }
            }
        }
    }

}
