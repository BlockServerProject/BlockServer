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


public class EventListener<A, B> {
    private Class<A> _listenerType;
    private Priority _priority = Priority.NORMAL;
    private boolean _post;

    public Class<A> getListenerType() {
        return _listenerType;
    }

    public boolean isPost() {
        return _post;
    }

    public Priority getPriority() {
        return _priority;
    }

    public void onEvent(B event) {
    }

    public EventListener<A, B> post() {
        _post = !_post;
        return this;
    }

    public EventListener<A, B> priority(Priority priority) {
        _priority = priority;
        return this;
    }

    public EventListener<A, B> register(Class<A> listenerType, EventManager eventManager) {
        _listenerType = listenerType;
        eventManager.registerListener(this);
        return this;
    }

    public void unregister(EventManager eventManager) {
        eventManager.unregisterListener(this);
    }
}