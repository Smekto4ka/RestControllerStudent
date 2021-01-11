package ru.oogis.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "SubjectBinder")
public class SubjectBinder {

    @Id
    @Column(name = "binderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long binderId;

    @OneToMany(orphanRemoval = true , fetch = FetchType.EAGER)
    private List<Marks> marksList;

    @ManyToOne(fetch = FetchType.EAGER)
    private Subject subject;

    public SubjectBinder() {
    }

    public long getBinderId() {
        return binderId;
    }

    public void setBinderId(long binderId) {
        this.binderId = binderId;
    }

    public List<Marks> getMarksList() {
        return marksList;
    }

    public void setMarksList(List<Marks> marksList) {
        this.marksList = marksList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public SubjectBinder(Subject subject) {
        this.subject = subject;
    }

    public void saveMarks(List<Marks> marksList) {
        if (this.marksList == null)
            this.marksList = new LinkedList<>();
        this.marksList.addAll(marksList);
    }

    public List<Marks> getMarks() {
        return marksList;
    }

    public List<Integer> getValueMarks() {
        return marksList.stream().map(Marks::getMarks).collect(Collectors.toList());
    }

    public OptionalDouble getAverage() {
        return marksList.stream().mapToInt(Marks::getMarks).average();
    }

    public SubjectEnum getNameSubject() {
        return subject.getSubjectName();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(subject.getSubjectName() + ": ");
        for (Marks marks : marksList) {
            stringBuilder.append(marks.getMarks() + "; ");
        }
        return stringBuilder.toString();
    }
}
