package net.blockserver.utility;

public enum MinecraftVersion {
	V081, V090, V091, V092, V093, V094;
	
	public static String versionToString(MinecraftVersion version){
		if(version == V081){
			return "0.8.1";
		}
		else if(version == V090){
			return "0.9.0";
		}
		else if(version == V091){
			return "0.9.1";
		}
		else if(version == V092){
			return "0.9.2";
		}
		else if(version == V093){
			return "0.9.3";
		}
		else if(version == V094){
			return "0.9.4";
		}
		else{
			return null;
		}
	}

}
