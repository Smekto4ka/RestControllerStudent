package ru.oogis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oogis.model.SubjectEnum;
import ru.oogis.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Subject findBySubjectName(SubjectEnum subjectName);

    boolean existsBySubjectName(SubjectEnum subjectEnum);

}
