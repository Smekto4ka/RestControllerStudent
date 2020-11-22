package ru.oogis.model.form;

import ru.oogis.model.SubjectEnum;

public interface ParametersForFilter {

    SubjectEnum getPredmet();

    Double getMinimumBorder();

    Double getMaximumBorder();

    boolean isCheck();
}
