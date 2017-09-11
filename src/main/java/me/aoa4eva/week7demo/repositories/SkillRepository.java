package me.aoa4eva.week7demo.repositories;

import me.aoa4eva.week7demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository<Skills,Long> {
}
