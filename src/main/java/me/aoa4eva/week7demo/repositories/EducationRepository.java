package me.aoa4eva.week7demo.repositories;

import me.aoa4eva.week7demo.models.Education;
import me.aoa4eva.week7demo.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepository  extends CrudRepository<Education,Long> {
}
