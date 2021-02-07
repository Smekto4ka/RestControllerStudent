package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;
import ru.oogis.event.StudentUpdateEvent;
import ru.oogis.model.Student;
import ru.oogis.model.form.FormAndIdClient;
import ru.oogis.model.form.FormListMarks;
import ru.oogis.service.StudentService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
//@RequestMapping("/webSocket")
public class WebSocketController {
    private final StudentService studentService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public WebSocketController(StudentService studentService, ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.studentService = studentService;
    }


    /*
    одноразовый метод, происходит при
    subscribe("/app/topic/update , ....");
     */
/*    @SubscribeMapping("/topic/update")
    public void subscribe(Principal principal) {
        System.out.println(principal);

    }*/


    @MessageMapping("/hello")
    @SendTo("/topic/connect")
    public Greeting greeting(HelloMessage message) throws Exception {

        return new Greeting("Hello, connect " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/all")
    @SendToUser("/queue/student/all")
    public List<Student> getStudents(Principal principal, long idClient) {
        List<Student> students = studentService.getStudents();
        return students;

    }

    public Student getStudentById(@PathVariable long studentId) {
        return studentService.getStudById(studentId).get();
    }

    @MessageMapping("/update")
    @SendTo("/topic/update")
    public Student updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);

       /* StudentUpdateEvent studentCreatEvent = new StudentUpdateEvent(student);
        applicationEventPublisher.publishEvent(studentCreatEvent);*/
        return student;

    }

    @MessageMapping("/postStudent")
    @SendToUser("/queue/post")
    public String postStudent(@Valid @RequestBody FormAndIdClient<Student> form) {


        studentService.postStudent(form.getBody());
        //TODO если ок , то ок иначе текст ошибки
        String info = "ok";
        if (info.equals("ok")) {
            StudentUpdateEvent studentCreatEvent = new StudentUpdateEvent(form.getBody());
            applicationEventPublisher.publishEvent(studentCreatEvent);
        }
        return info;

    }


   /* @MessageMapping("/postStudent")
    public void postStudent(Principal principal, @Valid @RequestBody FormAndIdClient<Student> form) {


        studentService.postStudent(form.getBody());
        //TODO если ок , то ок иначе текст ошибки
        String info = "ok";
        if (info.equals("ok")) {
            StudentUpdateEvent studentCreatEvent = new StudentUpdateEvent(form.getBody());
            applicationEventPublisher.publishEvent(studentCreatEvent);
        }

        ErrorCreatEvent errorCreatEvent = new ErrorCreatEvent(info, principal);
        applicationEventPublisher.publishEvent(errorCreatEvent);
    }*/


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
