package ru.oogis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.oogis.model.Marks;
import ru.oogis.model.SubjectEnum;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

}
