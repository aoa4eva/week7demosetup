package me.aoa4eva.week7demo.repositories;

import me.aoa4eva.week7demo.models.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<UserRole,Long> {
    UserRole findByRoleName(String roleName);
}
