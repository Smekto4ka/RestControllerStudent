package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import ru.oogis.event.ErrorCreatEvent;
import ru.oogis.event.StudentListEvent;
import ru.oogis.event.StudentUpdateEvent;
import ru.oogis.model.Student;
import ru.oogis.model.form.FormAndIdClient;
import ru.oogis.model.form.FormListMarks;
import ru.oogis.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/webSocket")
public class WebSocketController {
    private final StudentService studentService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public WebSocketController(StudentService studentService, ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.studentService = studentService;
    }


    @MessageMapping("/hello")
    @SendTo("/topic/connect")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, connect " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/all")
    public void getStudents(long idClient) {
        System.out.println(idClient);
        List<Student> students = studentService.getStudents();
        StudentListEvent studentListEvent = new StudentListEvent(students, idClient);
        applicationEventPublisher.publishEvent(studentListEvent);
    }

    public Student getStudentById(@PathVariable long studentId) {
        return studentService.getStudById(studentId).get();
    }

    @MessageMapping("/update")
    //@SendTo("/topic/update")
    public void updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);

        StudentUpdateEvent studentCreatEvent = new StudentUpdateEvent(student);
        applicationEventPublisher.publishEvent(studentCreatEvent);
        //return student;

    }

    @MessageMapping("/postStudent")
    //  @SendTo("/topic/update")
    public void postStudent(@Valid @RequestBody FormAndIdClient<Student> form) {


        studentService.postStudent(form.getBody());
        //TODO если ок , то ок иначе текст ошибки
        String info = "ok";
        ErrorCreatEvent errorCreatEvent = new ErrorCreatEvent(info, form.getIdClient());
        applicationEventPublisher.publishEvent(errorCreatEvent);


        StudentUpdateEvent studentCreatEvent = new StudentUpdateEvent(form.getBody());
        applicationEventPublisher.publishEvent(studentCreatEvent);

        //return student;

    }

    @MessageMapping("/delete")
    @SendTo("/topic/delete")
    public long deleteStudent(long studentId) {
        //System.out.println(studentId);
        studentService.deleteStudentById(studentId);
        // return studentId;
        return studentId;
    }

    @MessageMapping("/save/marks")
    @SendTo("/topic/update")
    public Student saveMarks(@RequestBody FormListMarks formListMarks) {
        studentService.setMarksByIdStudentsAndSubject(formListMarks.getStudentId(), formListMarks.getPredmet(), formListMarks.getList());
        return getStudentById(formListMarks.getStudentId());
    }
}

class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
