package ru.oogis.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private SubjectEnum subjectName;




    public Subject(SubjectEnum subjectName) {
        this.subjectName = subjectName;
    }

    public Subject() {
    }


    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public SubjectEnum getSubjectName() {
        return subjectName;
    }


    public void setSubjectName(SubjectEnum subjectName) {
        this.subjectName = subjectName;
    }




}
