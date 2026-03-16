package com.example.baitap7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.baitap7.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
