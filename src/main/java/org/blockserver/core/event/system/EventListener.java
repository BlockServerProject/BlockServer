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