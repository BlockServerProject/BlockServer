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
package org.blockserver.core.event.system;


import java.util.Set;
import java.util.TreeSet;

public class EventManager {
    private Set<EventListener<?, ?>> instances = new TreeSet<>((o1, o2) -> Integer.compare(o2.getPriority().ordinal(), o1.getPriority().ordinal()));

    public EventManager() {
    }

    public void registerListener(EventListener<?, ?> listener) {
        instances.add(listener);
    }

    public void unregisterListener(EventListener listener) {
        instances.remove(listener);
    }

    @SuppressWarnings("unchecked")
    public <A, B> B fire(Class<A> listenerType, B event, EventExecutor<B> executor) {
        instances.stream().filter(l -> l.getListenerType().isAssignableFrom(listenerType)).forEach(l -> {
            EventListener<A, B> listener = (EventListener<A, B>) l;
            if (listener.isPost())
                listener.onEvent(event);
            else {
                if (executor != null)
                    executor.execute(event);
                listener.onEvent(event);
            }
        });
        return event;
    }

    public <B> B fire(B event, EventExecutor<B> executor) {
        return fire(event.getClass(), event, executor);
    }

    public <A, B> B fire(Class<A> listenerType, B event) {
        return fire(listenerType, event, null);
    }

    public <B> B fire(B event) {
        return fire(event.getClass(), event, null);
    }
}