package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import ru.oogis.model.Student;
import ru.oogis.model.SubjectEnum;
import ru.oogis.model.form.FormListMarks;
import ru.oogis.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/webSocket")
public class WebSocketController {
    private final StudentService studentService;

    @Autowired
    public WebSocketController(StudentService studentService) {
        this.studentService = studentService;
    }


    @MessageMapping("/hello")
    @SendTo("/topic/connect")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, connect " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    public Student getStudentById(@PathVariable long studentId) {
        return studentService.getStudById(studentId).get();
    }

    @MessageMapping("/update")
    @SendTo("/topic/update")
    public Student updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
        return student;

    }

    @PostMapping()
    @SendTo("/topic/addStudent")
    public Student postStudent(@Valid @RequestBody Student student) {

        studentService.postStudent(student);

        return student;

    }

    @DeleteMapping("/{studentId}")
    @SendTo("/topic/delete")
    public long deleteStudent(@PathVariable long studentId) {
        studentService.deleteStudentById(studentId);
        return studentId;
    }

    @PutMapping("/save/marks/{studentId}")
    @SendTo("/topic/update")
    public Student saveMarks(@PathVariable long studentId, @RequestBody FormListMarks formListMarks) {

        studentService.setMarksByIdStudentsAndSubject(studentId, formListMarks.getPredmet(), formListMarks.getList());
        return getStudentById(studentId);
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