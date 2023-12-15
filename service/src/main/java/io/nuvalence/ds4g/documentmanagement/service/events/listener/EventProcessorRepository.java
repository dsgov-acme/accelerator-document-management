package io.nuvalence.ds4g.documentmanagement.service.events.listener;

import io.nuvalence.ds4g.documentmanagement.service.events.listener.processors.InitiateDocumentProcessingEventProcessor;
import io.nuvalence.ds4g.documentmanagement.service.util.SpringApplicationContext;
import io.nuvalence.events.event.Event;
import io.nuvalence.events.event.InitiateDocumentProcessingEvent;
import io.nuvalence.events.exception.EventProcessingException;
import io.nuvalence.events.subscriber.EventProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for selecting the processor that corresponds to a given event.
 */
@Component
public class EventProcessorRepository {
    private Map<String, Class<?>> eventProcessorsMap;

    /**
     * Creates a new instance of the EventProcessorGetter and initialize the mapping between
     * event types and processors.
     */
    public EventProcessorRepository() {
        this.eventProcessorsMap = new HashMap<>();
        this.eventProcessorsMap.put(
                InitiateDocumentProcessingEvent.class.getSimpleName(),
                InitiateDocumentProcessingEventProcessor.class);
    }

    /**
     * Returns the processor that corresponds to the event type.
     * @param event Event from which the type is obtained
     * @return EventProcessor
     * @throws EventProcessingException if an error occurs during event processing.
     */
    public EventProcessor get(Event event) throws EventProcessingException {
        try {
            Class<?> eventProcessorClass = eventProcessorsMap.get(event.getMetadata().getType());
            EventProcessor eventProcessor =
                    (EventProcessor) SpringApplicationContext.getBeanByClass(eventProcessorClass);
            eventProcessor.setData(event);
            return eventProcessor;
        } catch (Exception e) {
            throw new EventProcessingException(e);
        }
    }
}
