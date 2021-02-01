package ru.oogis.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class StudentListEventHandler implements ApplicationListener<StudentListEvent> {

    private final Log log = LogFactory.getLog(getClass());
    private final SimpMessagingTemplate webSocket;

    public StudentListEventHandler(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void onApplicationEvent(StudentListEvent studentListEvent) {
        log.info("отсыл листа студентов клиенту : " + studentListEvent.getIdClient());
        webSocket.convertAndSendToUser(studentListEvent.getIdClient().toString(), "/student/all", studentListEvent.getStudentList());

    }
}
