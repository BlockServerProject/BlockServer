package net.blockserver.utility;

public enum MinecraftVersion {
	V081, V090;
	
	public static String versionToString(MinecraftVersion version){
		if(version == V081){
			return "0.8.1";
		}
		else if(version == V090){
			return "0.9.0";
		}
		else{
			return null;
		}
	}

}
