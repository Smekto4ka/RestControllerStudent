package ru.oogis.event;

import org.springframework.context.ApplicationEvent;
import ru.oogis.model.Student;

import java.util.List;

public class StudentListEvent extends ApplicationEvent {

    private List<Student> studentList;
    private long idClient;

    public StudentListEvent(List<Student> studentList , long idClient){
        super(studentList);
        this.studentList = studentList;
        this.idClient = idClient;
    }

    public List<Student> getStudentList() {
        return studentList;
    }


    public Long getPrincipal() {
        return idClient;
    }

}
