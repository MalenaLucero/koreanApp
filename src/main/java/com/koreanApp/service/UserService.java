package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.User;
import com.koreanApp.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getAll(){
        return userRepository.findAll();
    }
	
	public Optional<User> getByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> getById(Long id){
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
}
