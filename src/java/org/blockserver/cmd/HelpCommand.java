package org.blockserver.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HelpCommand extends Command{
	@Override
	public String getName(){
		return "help";
	}

	@Override
	public CharSequence run(CommandIssuer issuer, List<String> args){
		Collection<Command> cmds = issuer.getServer().getCmdManager().getCommands().values();
		List<CharSequence> list = new ArrayList<CharSequence>(cmds.size());
		for(Command cmd: cmds){
			StringBuilder appender = new StringBuilder();
			appender.append("/");
			appender.append(cmd.getName());
			appender.append(" ");
			appender.append(cmd.getDescription());
			list.add(appender);
		}
		int page = 1;
		if(args.size() >= 1){
			page = Integer.parseInt(args.remove(0));
		}
		LineWrapResult result = wrapLines(list, issuer.getHelpLines(), page, issuer.getEOL());
		return String.format("Showing help page %d of %d:%s%s", page, result.getMax(), issuer.getEOL(), result.getResult().toString());
	}

	public static LineWrapResult wrapLines(List<CharSequence> list, int linesPerPage, int page){
		return wrapLines(list, linesPerPage, page, "\n");
	}
	public static LineWrapResult wrapLines(List<CharSequence> list, int linesPerPage, int page, String connector){
		int max = (int) Math.ceil(((double) list.size()) / linesPerPage);
		list = new ArrayList<CharSequence>(list);
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < linesPerPage; i++){
			try{
				builder.append(list.remove((page - 1) * linesPerPage));
				builder.append(connector);
			}
			catch(IndexOutOfBoundsException e){
				break;
			}
		}
		return new LineWrapResult(builder, max);
	}
	public static class LineWrapResult{
		private CharSequence result;
		private int max;

		public LineWrapResult(CharSequence result, int max){
			this.result = result;
			this.max = max;
		}

		public CharSequence getResult(){
			return result;
		}
		public int getMax(){
			return max;
		}
	}
}
