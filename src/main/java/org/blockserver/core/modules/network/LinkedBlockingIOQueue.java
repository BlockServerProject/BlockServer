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
package org.blockserver.core.modules.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Written by Exerosis!
 */
public class LinkedBlockingIOQueue<T> {
    private final BlockingQueue<T> input = new LinkedBlockingQueue<>();
    private final BlockingQueue<T> output = new LinkedBlockingQueue<>();

    public void queueInbound(T object) {
        input.add(object);
    }

    public void queueOutbound(T object) {
        output.add(object);
    }


    public T pollInbound() {
        return input.poll();
    }

    public T pollOutbound() {
        return output.poll();
    }


    public T peekInbound() {
        return input.peek();
    }

    public T peekOutbound() {
        return output.peek();
    }
}