package com.example.medicalbookingsystem.dao;

import com.example.medicalbookingsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(int theId);
}
