package me.aoa4eva.week7demo.repositories;
import me.aoa4eva.week7demo.models.Jobs;
import me.aoa4eva.week7demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SkillRepository extends CrudRepository<Skills,Long> {
    Iterable <Skills> findAllByJskillsNotIn(Set<Jobs> jobList);



}
