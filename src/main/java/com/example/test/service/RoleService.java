package com.example.test.service;

import com.example.test.entity.Role;
import com.example.test.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRopository;

    public List<Role> getAll() {
        return roleRopository.findAll();
    }

    public Role getRole(String name) throws Exception {
        return roleRopository.findById(name).orElseThrow(() -> new Exception("Not found"));
    }

}
