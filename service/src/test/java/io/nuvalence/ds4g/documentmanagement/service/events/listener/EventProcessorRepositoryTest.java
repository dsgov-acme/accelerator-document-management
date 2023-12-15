package io.nuvalence.ds4g.documentmanagement.service.events.listener;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.nuvalence.ds4g.documentmanagement.service.events.listener.processors.InitiateDocumentProcessingEventProcessor;
import io.nuvalence.ds4g.documentmanagement.service.util.SpringApplicationContext;
import io.nuvalence.events.event.EventMetadata;
import io.nuvalence.events.event.InitiateDocumentProcessingEvent;
import io.nuvalence.events.exception.EventProcessingException;
import io.nuvalence.events.subscriber.EventProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventProcessorRepositoryTest {
    private EventProcessorRepository eventProcessorRepository;

    @BeforeEach
    void setUp() {
        eventProcessorRepository = new EventProcessorRepository();
    }

    @Test
    void testGet() throws EventProcessingException {
        InitiateDocumentProcessingEvent event = InitiateDocumentProcessingEvent.builder().build();
        EventMetadata metadata =
                EventMetadata.builder()
                        .type(InitiateDocumentProcessingEvent.class.getSimpleName())
                        .build();
        event.setMetadata(metadata);

        try (MockedStatic<SpringApplicationContext> mocked =
                Mockito.mockStatic(SpringApplicationContext.class)) {
            InitiateDocumentProcessingEventProcessor expectedProcessor =
                    new InitiateDocumentProcessingEventProcessor(null);

            mocked.when(
                            () ->
                                    SpringApplicationContext.getBeanByClass(
                                            InitiateDocumentProcessingEventProcessor.class))
                    .thenReturn(expectedProcessor);

            EventProcessor eventProcessor = eventProcessorRepository.get(event);

            assertTrue(eventProcessor instanceof InitiateDocumentProcessingEventProcessor);
        }
    }
}
