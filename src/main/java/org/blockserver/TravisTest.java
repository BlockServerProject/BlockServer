package org.blockserver;

public class TravisTest{
	public static void main(String[] args){
		System.out.println("Running org.blockserver.TravisTest");
		new Thread(() -> {
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				e.printStackTrace();
				System.exit(1);
			}
			System.exit(0);
		}).start();
		run.main(new String[0]);
		System.out.println("Finished!");
	}
}
