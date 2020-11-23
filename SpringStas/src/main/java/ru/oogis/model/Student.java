package ru.oogis.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.*;

import java.util.stream.Collectors;

/**
 * Student object. Which has a unique id.
 * <p>
 * Also, the student has grades in subjects and their average value.
 */
@Entity
@Table(name = "Students")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;
    @NotNull
    @Column(name = "firstName")
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Column(name = "lastName")
    @Size(min = 2, max = 20)
    private String lastName;

    @Column(name = "years")
    @Min(0)
    @Max(150)
    private int years;

    @OneToMany(orphanRemoval = true)
    private Map<Integer, SubjectBinder> subjectBinderMap;

    public Student(long studentId, String firstName, String lastName, int years) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.years = years;
    }

    public Student() {
    }


    public Map<Integer, SubjectBinder> getSubjectBinderMap() {
        return subjectBinderMap;
    }

    public void setSubjectBinderMap(Map<Integer, SubjectBinder> binderMap) {
        this.subjectBinderMap = binderMap;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setStudentId(long idStudent) {
        this.studentId = idStudent;
    }

    public long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public List<String> getInfoSubject() {
        List<String> info = subjectBinderMap.values().stream()
                .map(SubjectBinder::toString).collect(Collectors.toList());
        if (info.size() == 0) {
            info.add("no marks");
        }

        return info;
    }

    public void update(Student student) {

        firstName = student.firstName;
        lastName = student.lastName;
        years = student.years;
    }

    public boolean chekSubjectBinder(int subjectId) {
        return subjectBinderMap.containsKey(subjectId);

    }

    public SubjectBinder newSubjectBinder(Subject subject) {
        SubjectBinder subjectBinder = new SubjectBinder(subject);
        subjectBinderMap.put(subject.getSubjectId(), subjectBinder);
        return subjectBinder;
    }

    public List<SubjectBinder> getSubjectBinderAll() {
        return new LinkedList<>(subjectBinderMap.values());
    }

    public SubjectBinder getSubjectBinderById(int subjectId) {
        return subjectBinderMap.get(subjectId);
    }

    public OptionalDouble getAverageBySubject(SubjectEnum subjectEnum) {
        for (SubjectBinder subjectBinder : subjectBinderMap.values()) {
            if (subjectBinder.getNameSubject().equals(subjectEnum))
                return subjectBinder.getAverage();
        }
        return OptionalDouble.empty();
    }

    public OptionalDouble getAverageBySubject(int subjectId) {
        if (subjectBinderMap.containsKey(subjectId)) {
            return subjectBinderMap.get(subjectId).getAverage();
        }
        return OptionalDouble.empty();
    }
}
