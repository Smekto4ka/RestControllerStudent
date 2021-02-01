package ru.oogis.event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class UpdateEventHandler implements ApplicationListener<StudentUpdateEvent> {
   private final Log log = LogFactory.getLog(getClass());
   private final SimpMessagingTemplate webSocket;

    public UpdateEventHandler(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void onApplicationEvent(StudentUpdateEvent event) {
        log.info("Received spring custom event - " + event.getStudent());
        this.webSocket.convertAndSend("/topic/update"  , event.getStudent());
    }
}
