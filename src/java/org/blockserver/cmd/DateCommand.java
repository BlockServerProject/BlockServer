
import java.util.List;

import org.blockserver.Server;
import org.blockserver.player.Player;


public class DateCommand extends Command{
  
  	public String getDate() {
		return "time";
		
	}


      public String run(CommandIssuer issuer, Date<String> args){
        Date = date = new Date();
        
      String returnDateString = "Time: ";
      Server server = issuer.getServer();
      returnDateString = returnDateString + date.toString();
      
      return returnDateString;
      
      }
    }
