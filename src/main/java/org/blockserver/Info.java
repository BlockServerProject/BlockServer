package org.blockserver;

public final class Info{
	public final static String SOFTWARE_NAME = "BlockServer";
	public final static String CURRENT_STAGE = "Beta";
	public final static String CURRENT_VERSION = "v1.0.0";
	public final static String CURRENT_BUILD = "Dev-Snapshot";
	public static String VERSION_STRING(){
		StringBuilder builder = new StringBuilder();
		if(!CURRENT_STAGE.isEmpty()){
			builder.append(CURRENT_STAGE).append(' ');
		}
		if(!CURRENT_VERSION.isEmpty()){
			builder.append(CURRENT_VERSION).append(' ');
		}
		if(!CURRENT_BUILD.isEmpty()){
			builder.append(CURRENT_BUILD).append(' ');
		}
		return builder.toString().trim();
	}
}
