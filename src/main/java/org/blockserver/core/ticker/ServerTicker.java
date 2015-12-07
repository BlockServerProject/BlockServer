/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core.ticker;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.Server;
import org.blockserver.core.modules.logging.LoggingModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Timekeeping class.
 */
public class ServerTicker implements Runnable {
    @Getter @Setter private boolean running = false;
    @Getter private long currentTick;
    private Server server;

    private List<Task> tasks = new ArrayList<>();

    public ServerTicker(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        server.getModule(LoggingModule.class).info("Ticker started.");
        while(isRunning()) {
            currentTick++;
            long start = System.currentTimeMillis();
            doTick();
            long elapsed = System.currentTimeMillis() - start;
            if(elapsed >= 50) server.getModule(LoggingModule.class).warn("Can't keep up! Did the system time change or is the server overloaded? ("+elapsed+">50)");
        }
        server.getModule(LoggingModule.class).info("Ticker stopped.");
    }

    public void registerTask(Task task) {
        task.setDelay(-1);
        synchronized (tasks) {
            tasks.add(task);
        }
    }

    public void registerRepeatingTask(Task task, int delay) {
        task.setDelay(delay);
        synchronized (tasks) {
            tasks.add(task);
        }
    }

    private void doTick() {
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.getDelay() == -1) {
                    task.run();
                    tasks.remove(task);
                    continue;
                }
                if ((currentTick - task.getLastTickRan()) >= task.getDelay()) {
                    task.run();
                    task.setLastTickRan(currentTick);
                }
            }
        }
    }
}
