package ru.oogis.model;

import ru.oogis.model.form.ParametersForFilter;
import ru.oogis.model.form.SetPredicate;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum FilterCriterion {
    AVERAGE_MARKS {
        @Override
        public List<Student> getStudentUsingFilter(List<Student> studentList, ParametersForFilter parametersForFilter) {
            Predicate<OptionalDouble> predicate = SetPredicate.getPredicateFilterOptionDouble(parametersForFilter);
            return studentList.stream()
                    .filter(student -> predicate.test(student.getAverageBySubject(parametersForFilter.getPredmet())))
                    .collect(Collectors.toList());
        }
    },
    YEARS {
        @Override
        public List<Student> getStudentUsingFilter(List<Student> studentList, ParametersForFilter parametersForFilter) {
            Predicate<Integer> predicate = SetPredicate.getPredicateFilterInt(parametersForFilter);
            return studentList.stream()
                    .filter(student -> predicate.test(student.getYears()))
                    .collect(Collectors.toList());
        }
    };

    public abstract List<Student> getStudentUsingFilter(List<Student> studentList, ParametersForFilter parametersForFilter);

}
