package me.aoa4eva.week7demo.repositories;

import me.aoa4eva.week7demo.models.Person;
import me.aoa4eva.week7demo.models.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PersonRepository extends CrudRepository<Person,Long> {

    Iterable<Person> findAllByRoleListContains(UserRole r);
    Iterable <Person> findAllByRoleListIn(Set <UserRole> roleList);
}
