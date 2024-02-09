package alma.api;

import alma.events.AlmaEvent;
import alma.events.AlmaEventType;

/**
 * API for external listener objects to be notified of events within Alma.
 *
 * @author Santiago Barreiro
 */
public interface AlmaListener {

    /**
     * Returns the event types this listener is expecting to be notified of.
     * @return Array of event types this observer is expecting.
     */
    public AlmaEventType[] getEventType();

    /**
     * Returns the event types this listener is expecting to be notified of.
     * @return Boolean indicating if this observer is still observing for events.
     */
    public boolean isInterested();

    /**
     * Action to execute when notified of an event.
     * @param event Event that was notified
     */
    public void onNotify(AlmaEvent event);

    /**
     * Changes an internal flag to indicate this listener is no longer interested on Alma events.
     */
    public void noLongerInterested();
}
