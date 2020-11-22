package ru.oogis.model.form;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Predicate;

public class SetPredicate {

    public static Predicate<OptionalDouble> getPredicateFilterOptionDouble(ParametersForFilter parametersForFilter) {

        Predicate<OptionalDouble> predicate = OptionalDouble::isPresent;
        if (parametersForFilter.getMinimumBorder() != null && parametersForFilter.getMaximumBorder() != null) {
            predicate = predicate.and(optional -> optional.getAsDouble() > parametersForFilter.getMinimumBorder() && optional.getAsDouble() <= parametersForFilter.getMaximumBorder());
            if (parametersForFilter.isCheck())
                predicate = predicate.and(optional -> optional.getAsDouble() < parametersForFilter.getMinimumBorder() || optional.getAsDouble() >= parametersForFilter.getMaximumBorder());
        }
        if (parametersForFilter.getMinimumBorder() == null && parametersForFilter.getMaximumBorder() != null)
            predicate = predicate.and(optional -> optional.getAsDouble() < parametersForFilter.getMaximumBorder());
        if (parametersForFilter.getMinimumBorder() != null && parametersForFilter.getMaximumBorder() == null)
            predicate = predicate.and(optional -> optional.getAsDouble() > parametersForFilter.getMinimumBorder());
        return predicate;
    }

    public static Predicate<Integer> getPredicateFilterInt(ParametersForFilter parametersForFilter) {

        Predicate<Integer> predicate = Objects::nonNull;
        if (parametersForFilter.getMinimumBorder() != null && parametersForFilter.getMaximumBorder() != null) {
            predicate = predicate.and(optional -> optional > parametersForFilter.getMinimumBorder() && optional <= parametersForFilter.getMaximumBorder());
            if (parametersForFilter.isCheck())
                predicate = predicate.and(optional -> optional < parametersForFilter.getMinimumBorder() || optional >= parametersForFilter.getMaximumBorder());
        }
        if (parametersForFilter.getMinimumBorder() == null && parametersForFilter.getMaximumBorder() != null)
            predicate = predicate.and(optional -> optional < parametersForFilter.getMaximumBorder());
        if (parametersForFilter.getMinimumBorder() != null && parametersForFilter.getMaximumBorder() == null)
            predicate = predicate.and(optional -> optional > parametersForFilter.getMinimumBorder());
        return predicate;
    }
}
