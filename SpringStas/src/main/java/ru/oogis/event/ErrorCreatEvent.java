package ru.oogis.event;

import org.springframework.context.ApplicationEvent;

import java.security.Principal;

public class ErrorCreatEvent extends ApplicationEvent {
    private Object message;
    private  Principal principal;


    public ErrorCreatEvent(Object message , Principal principal){
        super(message);
        this.message = message;
        this.principal = principal;
    }

    public Object getMessage() {
        return message;
    }

    public Principal getPrincipal() {
        return principal;
    }
}
