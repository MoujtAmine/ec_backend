package org.d3h.application.repositories;

import org.d3h.application.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query("SELECT s FROM Student s WHERE s.apogee = ?1")
	Student findByApogee(String apogee);
	
	@Query("SELECT s FROM Student s WHERE s.cne = ?1")
	Student findByCne(String cne);
	
}
