package ru.oogis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.oogis.model.Student;
import ru.oogis.model.Subject;

import java.util.List;
import java.util.Set;

public interface StudentsRepository extends JpaRepository<Student, Long> {
    @Query("select s.studentId from Student s")
    List<Long> getSetIdStudent();


    @Query("select student from Student student where student.years >=:min and student.years <=:max")
    List<Student> getStudentByYears(@Param("min") Integer minYears, @Param("max") Integer maxEars);

    @Query("select student from Student student where student.years >=:min")
    List<Student> getStudentByMinYears(@Param("min") Integer minYears);

    @Query("select student from Student student where student.years <=:max")
    List<Student> getStudentByMaxYears(@Param("max") Integer maxYears);
}
