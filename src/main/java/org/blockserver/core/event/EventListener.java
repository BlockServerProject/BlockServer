package org.blockserver.core.event;


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
        new EventListener<String, Integer>() {
            public void onEvent(Integer event) {

            }
        }.post().register(String.class);
    }

    public EventListener<A, B> post() {
        _post = !_post;
        return this;
    }

    public EventListener<A, B> priority(Priority priority) {
        _priority = priority;
        return this;
    }

    public void register(Class<A> listenerType) {
        _listenerType = listenerType;
        GlobalEventManager.registerListener(this);
    }

    public void unregister() {
        GlobalEventManager.unregisterListener(this);
    }
}