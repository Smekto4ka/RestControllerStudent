package ru.oogis.model.form;


import ru.oogis.model.SubjectEnum;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FormListMarks {

    public FormListMarks() {

    }

    private long studentId;
    private SubjectEnum subjectEnum;
    @NotNull(message = "такого предмета нет")
    private String nameSubject;

    private Integer[] marks;

    public Integer[] getMarks() {
        return marks;
    }

    public void setMarks(Integer... mass) {
        this.marks = mass;
    }

    public List<Integer> getList() {
        return Arrays.stream(marks).filter(value -> value != null)
                //  .filter(value -> value >= 0 && value <= 5)
                .collect(Collectors.toCollection(() -> new LinkedList<>()));

    }

    public SubjectEnum getPredmet() {
        return subjectEnum;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        if (nameSubject.equals("")) {
            this.nameSubject = null;
            return;
        }
        this.nameSubject = nameSubject.toUpperCase().trim();

        try {
            this.subjectEnum = SubjectEnum.valueOf(this.nameSubject);

        } catch (IllegalArgumentException e) {
            System.out.println(this + " " + e);
            this.nameSubject = null;

        }

    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getLengthArraysMarks() {
        return marks.length;
    }

    @Override
    public String toString() {
        return "FormListMarks{" +
                "studentId=" + studentId +
                ", subjectEnum=" + subjectEnum +
                ", nameSubject='" + nameSubject + '\'' +
                ", marks=" + Arrays.toString(marks) +
                '}';
    }
}
