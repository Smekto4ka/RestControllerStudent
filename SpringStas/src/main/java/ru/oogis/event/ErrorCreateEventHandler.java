package ru.oogis.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ErrorCreateEventHandler implements ApplicationListener<ErrorCreatEvent> {

    private final Log log = LogFactory.getLog(getClass());
    private final SimpMessagingTemplate webSocket;

    public ErrorCreateEventHandler(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void onApplicationEvent(ErrorCreatEvent event) {
        log.info("Error " + event.getMessage());
        webSocket.convertAndSendToUser(event.getIdClient().toString(),"/queue/messages" , event.getMessage());
    }
}
