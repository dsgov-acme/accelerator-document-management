package io.nuvalence.ds4g.documentmanagement.service.events.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.nuvalence.ds4g.documentmanagement.service.events.listener.processors.InitiateDocumentProcessingEventProcessor;
import io.nuvalence.events.event.Event;
import io.nuvalence.events.event.EventMetadata;
import io.nuvalence.events.event.InitiateDocumentProcessingEvent;
import io.nuvalence.events.exception.EventProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class EventListenerImplTest {

    private EventListenerImpl eventListenerImpl;

    @Mock private EventProcessorRepository eventProcessorRepository;

    @Mock private InitiateDocumentProcessingEventProcessor initiateDocumentProcessingEvent;

    @BeforeEach
    void setUp() {
        eventListenerImpl = new EventListenerImpl(eventProcessorRepository);
    }

    @Test
    void testOnEvent() throws EventProcessingException {
        when(eventProcessorRepository.get(any())).thenReturn(initiateDocumentProcessingEvent);
        doNothing().when(initiateDocumentProcessingEvent).execute();

        Event event = InitiateDocumentProcessingEvent.builder().build();
        EventMetadata metadata =
                EventMetadata.builder()
                        .id(UUID.randomUUID())
                        .type("initiateDocumentProcessing")
                        .build();
        event.setMetadata(metadata);

        eventListenerImpl.onEvent(event);

        verify(eventProcessorRepository).get(any());
    }
}
