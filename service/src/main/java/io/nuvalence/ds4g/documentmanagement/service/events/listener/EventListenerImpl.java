package io.nuvalence.ds4g.documentmanagement.service.events.listener;

import io.nuvalence.events.event.Event;
import io.nuvalence.events.exception.EventProcessingException;
import io.nuvalence.events.subscriber.listener.EventListener;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Event listener implementation.
 */
@Component
@AllArgsConstructor
public class EventListenerImpl implements EventListener {
    private final EventProcessorRepository eventProcessorRepository;

    @Override
    public void onEvent(Event event) throws EventProcessingException {
        eventProcessorRepository.get(event).execute();
    }
}
