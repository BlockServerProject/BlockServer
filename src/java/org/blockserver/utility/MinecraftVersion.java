package org.blockserver.utility;

public enum MinecraftVersion {
	V081{
		@Override public String toString(){
			return "0.8.1";
		}
	},
	V090{
		@Override public String toString(){
			return "0.9.0";
		}
	},
	V091{
		@Override public String toString(){
			return "0.9.1";
		}
	},
	V092{
		@Override public String toString(){
			return "0.9.2";
		}
	},
	V093{
		@Override public String toString(){
			return "0.9.3";
		}
	},
	V094{
		@Override public String toString(){
			return "0.9.4";
		}
	},
	V095{
		@Override public String toString(){
			return "0.9.5";
		}
	},
	V0100{
		@Override public String toString(){
			return "0.10.0";
		}
	};

	public static String versionToString(MinecraftVersion version){
		return version.toString();
	}
}
