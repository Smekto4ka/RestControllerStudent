package ru.oogis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oogis.model.Student;
import ru.oogis.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/restStudent")
public class RestStudentController {

    private final StudentService studentService;

    @Autowired
    public RestStudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping()
    public List<Student> getIdStudent() {
        return studentService.getStudents();
    }

    @PutMapping()
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }
}
