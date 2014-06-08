package blockserver.core;

public class Player{
  public String ip;
  public int port;
  public String username;
  public int eid;
  public long cid;
  public float x;
  public float y;
  public float z;
  public float yaw;
  public float pitch;
  
  public Player(String i, int p, int e, long c){
	  /**
	   * Needs an IP, port, entity ID, and client ID
	   */
    ip = i;
    port = p;
    eid = e;
    cid = c;
  }
}
