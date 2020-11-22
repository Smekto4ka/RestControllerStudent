package ru.oogis.model;

import javax.persistence.*;

@Entity
@Table(name = "Marks")
public class Marks {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long marksId;

    private int marks;

    public Marks(int marks) {

        this.marks = marks;

    }

    public Marks() {
    }

    public long getMarksId() {
        return marksId;
    }

    public void setMarksId(long marksId) {
        this.marksId = marksId;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
