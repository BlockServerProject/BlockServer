package blockserver.utils.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class BlockLogger extends Formatter {

	@Override
	public String format(LogRecord rec) {
		String buffer = "";
		buffer = "("+getTime(rec.getMillis())+")["+rec.getLevel().getName()+"]: "+rec.getMessage()+"\n";
		return buffer;
	}
	
	private String getTime(long mili){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss:SSS");
		Date finalDate = new Date(mili);
		return dateFormat.format(finalDate);

	}

}
