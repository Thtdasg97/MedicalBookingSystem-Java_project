package com.example.asm03spring.service;

import com.example.asm03spring.dao.RoleRepository;
import com.example.asm03spring.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
