package com.example.medicalbookingsystem.service;

import com.example.medicalbookingsystem.dao.RoleRepository;
import com.example.medicalbookingsystem.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role findRoleById(int theId) {
        return roleRepository.findRoleById(theId);
    }

}
