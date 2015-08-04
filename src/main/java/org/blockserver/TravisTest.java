/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
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
