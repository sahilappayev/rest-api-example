package com.example.rest.repository;

import com.example.rest.entity.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Query(nativeQuery = true, value = "select * from roles r where r.name in ('ADMIN', 'USER')")
    Set<Role> findByNameIn(Set<String> names);

}
