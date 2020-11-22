package ru.oogis.model.form;


import ru.oogis.model.SubjectEnum;

import javax.validation.constraints.NotNull;

public class FormParametersForFilter implements ParametersForFilter {
    private boolean check;

    private Double minimumBorder;

    private Double maximumBorder;
    @NotNull(message = "нет такого предмета")
    private String nameSubject = "predmet"; // defolt
    private SubjectEnum subjectEnum;


    public FormParametersForFilter() {
    }

    @Override
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public Double getMinimumBorder() {
        return minimumBorder;
    }

    public void setMinimumBorder(Double minimumBorder) {
        this.minimumBorder = minimumBorder;
    }

    @Override
    public Double getMaximumBorder() {
        return maximumBorder;
    }

    public void setMaximumBorder(Double maximumBorder) {
        this.maximumBorder = maximumBorder;
    }

    @Override
    public SubjectEnum getPredmet() {
        return subjectEnum;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        if (nameSubject.equals("")) {
            this.nameSubject = null;
        }

        this.nameSubject = nameSubject.toUpperCase().trim();
        try {
            this.subjectEnum = SubjectEnum.valueOf(this.nameSubject);
        } catch (IllegalArgumentException e) {
            System.out.println(this + " " + e);
            this.nameSubject = null;

        }
    }


    @Override
    public String toString() {
        return "ParametersForFilter{" +
                "chek=" + check +
                ", min=" + minimumBorder +
                ", max=" + maximumBorder +
                ", predmet=" + subjectEnum +
                ", name predmet=" + nameSubject +
                '}';
    }
}
