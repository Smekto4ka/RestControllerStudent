package ru.oogis.event;

import org.springframework.context.ApplicationEvent;
import ru.oogis.model.Student;

public class ErrorCreatEvent extends ApplicationEvent {
    private Object message;
    private long idClient;


    public ErrorCreatEvent(Object message , long idClient){
        super(message);
        this.message = message;
        this.idClient = idClient;
    }

    public Object getMessage() {
        return message;
    }

    public Long getIdClient() {
        return idClient;
    }
}
