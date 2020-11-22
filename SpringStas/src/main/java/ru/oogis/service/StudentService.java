package ru.oogis.service;

import ru.oogis.model.FilterCriterion;
import ru.oogis.model.SubjectEnum;
import ru.oogis.model.Student;
import ru.oogis.model.form.ParametersForFilter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentService {
    /**
     * Returns the student object by id.
     */
    Optional<Student> getStudById(long studentId);

    /**
     * Returns the id of all available students.
     */
    List<Student> getStudents();

    /**
     * Gets the student object to add to the server.
     */
    public void postStudent(Student student);

    /**
     * adding new grades for a student
     *
     * @param studentId   student id
     * @param subjectEnum the item that is in the enum
     * @param marksList   list marks
     */
    void setMarksByIdStudentsAndSubject(long studentId, SubjectEnum subjectEnum, List<Integer> marksList);

    List<Student> getStudentsUsingFilter(ParametersForFilter parametersForFilter, FilterCriterion filterCriterion);


    boolean deleteStudentById(long id);

    boolean updateStudent(Student student);


}
