package com.example.baitap7.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.baitap7.model.Student;
import com.example.baitap7.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Student student = studentRepository.findByUsername(username);

        if (student == null) {
            throw new UsernameNotFoundException("Không tìm thấy user");
        }

        return new User(
                student.getUsername(),
                student.getPassword(),
                student.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
