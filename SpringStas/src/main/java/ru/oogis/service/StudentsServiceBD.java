package ru.oogis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.oogis.model.*;
import ru.oogis.model.form.ParametersForFilter;
import ru.oogis.repository.MarksRepository;
import ru.oogis.repository.StudentsRepository;
import ru.oogis.repository.SubjectBinderRepository;
import ru.oogis.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentsServiceBD implements StudentService {

    private MarksRepository marksRepository;
    private StudentsRepository studentsRepository;
    private SubjectRepository subjectRepository;
    private SubjectBinderRepository subjectBinderRepository;

    @Autowired
    public StudentsServiceBD(StudentsRepository studentsRepository
            , SubjectRepository subjectRepository, MarksRepository marksRepository
            , SubjectBinderRepository subjectBinderRepository) {
        this.subjectRepository = subjectRepository;
        this.studentsRepository = studentsRepository;
        this.marksRepository = marksRepository;
        this.subjectBinderRepository = subjectBinderRepository;
    }


    @Override
    public Optional<Student> getStudById(long studentId) {
        return studentsRepository.findById(studentId);
    }


    @Override
    public List<Student> getStudents() {
        return studentsRepository.findAll();
    }

    @Override
    public void postStudent(Student student) {
        studentsRepository.save(student);
    }


    @Override
    public void setMarksByIdStudentsAndSubject(long studentId, SubjectEnum subjectEnum, List<Integer> valueMarksList) {
        getStudById(studentId).ifPresent(student -> {
            if (!subjectRepository.existsBySubjectName(subjectEnum))
                subjectRepository.save(new Subject(subjectEnum));
            Subject subject = subjectRepository.findBySubjectName(subjectEnum);
            if (!student.chekSubjectBinder(subject.getSubjectId())) {
                subjectBinderRepository.save(student.newSubjectBinder(subject));
            }
            SubjectBinder subjectBinder = student.getSubjectBinderById(subject.getSubjectId());
            List<Marks> marksList = valueMarksList.stream().map(Marks::new).peek(marks -> marksRepository.save(marks)).collect(Collectors.toList());
            subjectBinder.saveMarks(marksList);
            updateSubjectBinder(subjectBinder);
            updateStudent(student);
        });
    }

    //todo сделать общую проверку и занести в enum
    @Override
    public List<Student> getStudentsUsingFilter(ParametersForFilter parametersForFilter,
                                                FilterCriterion filterCriterion) {
        return filterCriterion.getStudentUsingFilter(studentsRepository.findAll(), parametersForFilter);
    }

   /* private List<Student> getStudentUsingFilterByYears(ParametersForFilter parametersForFilter) {
        List<Student> studentSet = null;
        if (parametersForFilter.getMinimumBorder() != null && parametersForFilter.getMaximumBorder() != null) {
            if (parametersForFilter.isChek())
                studentSet = studentsRepository.getStudentByYears(parametersForFilter.getMaximumBorder().intValue(), parametersForFilter.getMinimumBorder().intValue());
            else
                studentSet = studentsRepository.getStudentByYears(parametersForFilter.getMinimumBorder().intValue(), parametersForFilter.getMaximumBorder().intValue());
        }
        if (parametersForFilter.getMinimumBorder() == null && parametersForFilter.getMaximumBorder() != null)
            studentSet = studentsRepository.getStudentByMaxYears(parametersForFilter.getMaximumBorder().intValue());
        if (parametersForFilter.getMinimumBorder() != null && parametersForFilter.getMaximumBorder() == null)
            studentSet = studentsRepository.getStudentByMinYears(parametersForFilter.getMinimumBorder().intValue());
        if (studentSet == null)
            studentSet = studentsRepository.findAll();
        return studentSet;
    }*/

    @Override
    public boolean deleteStudentById(long studentId) {
        if (studentsRepository.existsById(studentId)) {
            studentsRepository.deleteById(studentId);

            return true;
        }
        return false;
    }

    @Override
    public boolean updateStudent(Student newStudent) {
        if (studentsRepository.existsById(newStudent.getStudentId())) {
            studentsRepository.findById(newStudent.getStudentId()).ifPresent(student -> {
                newStudent.setSubjectBinderMap(student.getSubjectBinderMap());
                studentsRepository.save(newStudent);
            });
            return true;
        }
        return false;
    }

    private void updateSubjectBinder(SubjectBinder subjectBinder) {
        subjectBinderRepository.save(subjectBinder);
    }
}
