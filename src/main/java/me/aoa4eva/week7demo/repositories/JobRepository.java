package me.aoa4eva.week7demo.repositories;

import me.aoa4eva.week7demo.models.Jobs;
import me.aoa4eva.week7demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Jobs,Long> {
    Iterable <Jobs>findAllBySkillsIn(Iterable <Skills> skillList);
}
