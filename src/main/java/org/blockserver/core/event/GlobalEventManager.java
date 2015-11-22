package org.blockserver.core.event;

import java.util.Set;
import java.util.TreeSet;


public class GlobalEventManager {
    private static Set<EventListener<?, ?>> instances = new TreeSet<>((o1, o2) -> Integer.compare(o2.getPriority().ordinal(), o1.getPriority().ordinal()));

    private GlobalEventManager() {
    }

    public static void registerListener(EventListener<?, ?> listener) {
        instances.add(listener);
    }

    public static void unregisterListener(EventListener listener) {
        instances.remove(listener);
    }

    @SuppressWarnings("unchecked")
    public static <A, B> B fire(Class<A> listenerType, B event, EventExecutor<B> executor) {
        instances.stream().filter(l -> l.getListenerType().isAssignableFrom(listenerType)).forEach(l -> {
            EventListener<A, B> listener = (EventListener<A, B>) l;
            if (listener.isPost())
                listener.onEvent(event);
            else {
                executor.execute(event);
                listener.onEvent(event);
            }
        });
        return event;
    }

    public static <B> B fire(B event, EventExecutor<B> executor) {
        return fire(event.getClass(), event, executor);
    }

    public static <A, B> B fire(Class<A> listenerType, B event) {
        return fire(listenerType, event, null);
    }

    public static <B> B fire(B event) {
        return fire(event.getClass(), event, null);
    }
}
