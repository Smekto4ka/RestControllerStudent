package ru.oogis.event;

import org.springframework.context.ApplicationEvent;
import ru.oogis.model.Student;

public class StudentUpdateEvent extends ApplicationEvent {

    private Student student;

    public StudentUpdateEvent(Student student) {
        super(student);
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
