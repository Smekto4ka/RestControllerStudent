package ru.oogis.service;

import ru.oogis.model.FilterCriterion;
import ru.oogis.model.SubjectEnum;
import ru.oogis.model.Student;
import ru.oogis.model.form.ParametersForFilter;
import ru.oogis.model.form.SetPredicate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//@Service
public class SimpleStudentService implements StudentService {
    private static Map<Long, Student> studentMap = new HashMap<>();
    private static long maxIdStudent;

    static {
        studentMap.put(1l, new Student(1, "Stas", "last name", 5));
        studentMap.put(2l, new Student(2, "Sasa", "last name", 15));
        studentMap.put(3l, new Student(3, "Misa", "last name", 5));
        studentMap.put(4l, new Student(4, "Petya", "stepanov", 25));
        studentMap.put(5l, new Student(5, "Kiril", "stepanov", 25));
        studentMap.put(6l, new Student(6, "Misa", "Antonov", 35));
        studentMap.put(7l, new Student(7, "Oleg", "Antonov", 2555));
        studentMap.put(8l, new Student(8, "Lilya", "Alexeev", 25));
        List<Integer> marksList = new LinkedList<>();
        marksList.add(5);
        marksList.add(7);
        marksList.add(3);
/*        studentMap.get(1L).setMarks(Predmet.HISTORY, marksList);
        studentMap.get(1L).setMarks(Predmet.PHYSIC, marksList);
        studentMap.get(3L).setMarks(Predmet.PHYSIC, marksList);*/
        maxIdStudent = 8;
    }


    @Override
    public Optional<Student> getStudById(long studentId) {
        return Optional.ofNullable(studentMap.get(studentId));
    }

    @Override
    public List<Student> getStudents() {
        return new LinkedList<>(studentMap.values());
    }

    @Override
    public void postStudent(Student student) {
        long id = ++maxIdStudent;
        student.setStudentId(id);
        studentMap.put(id, student);
    }

    @Override
    public void setMarksByIdStudentsAndSubject(long studentId, SubjectEnum subjectEnum, List<Integer> marksList) throws NullPointerException {
     /*   Student student = studentMap.get(studentId);
        student.setMarks(predmet, marksList);*/
    }

    /**
     * Возвращает список id студентов, прошедших проверку по критериям.
     *
     * @param parametersForFilter объект с значениями ограничений для фильтра
     * @return
     */
    @Override
    public List<Student> getStudentsUsingFilter(ParametersForFilter parametersForFilter, FilterCriterion filterCriterion) {
        return filterCriterion.getStudentUsingFilter(new LinkedList<>(studentMap.values()), parametersForFilter);
    }


    @Override
    public boolean deleteStudentById(long id) {
        if (studentMap.remove(id) != null)
            return true;
        return false;
    }

    @Override
    public boolean updateStudent(Student student) {
        studentMap.get(student.getStudentId()).update(student);
        return true;
    }

    @Override
    public String toString() {
        if (studentMap.isEmpty()) return "not stud";
        return studentMap.values().stream().map(Student::toString).collect(Collectors.joining("\n"));
    }
}
