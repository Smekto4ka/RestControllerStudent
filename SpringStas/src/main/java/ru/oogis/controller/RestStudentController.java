package ru.oogis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.oogis.model.Student;
import ru.oogis.model.SubjectEnum;
import ru.oogis.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable long studentId) {
        return studentService.getStudById(studentId).get();
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return studentService.updateStudent(student) ? new ResponseEntity<>(student, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping()
    public ResponseEntity<?> postStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        studentService.postStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable long studentId) {
        return studentService.deleteStudentById(studentId) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/subject")
    public SubjectEnum[] getNameSubject() {
        return SubjectEnum.values();
    }
}
