package org.blockserver.api;

/**
 * Represents an Event. Must be implemented by ALL event implementations.
 */
public interface Event {

    /**
     * Check to see if this event is canceled or not.
     * @return If the event is canceled
     */
    boolean isCanceled();

    /**
     * Set this event to be canceled.
     */
    void cancel();
}
