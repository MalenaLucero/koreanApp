package com.koreanApp.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreanApp.entity.Role;
import com.koreanApp.entity.User;
import com.koreanApp.enums.RoleEnum;
import com.koreanApp.payload.SetRolesRequest;
import com.koreanApp.repository.RoleRepository;
import com.koreanApp.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/user")
	public @ResponseBody ResponseEntity<Object> getAllUsers() {
		try {
			Iterable<User> users = userService.getAll();
			return new ResponseEntity<Object>(users, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/user/set-roles")
	public @ResponseBody ResponseEntity<Object> setUserRoles(@RequestBody SetRolesRequest setRolesRequest){
		Optional<User> user = userService.getByUsername(setRolesRequest.getUsername());
		if(user.isPresent()) {
			User userToUpdate = user.get();
			Set<String> strRoles = setRolesRequest.getRoles();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				return new ResponseEntity<>("Missing user roles", HttpStatus.BAD_REQUEST);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "develop":
						Role devRole = roleRepository.findByName(RoleEnum.ROLE_DEVELOP)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(devRole);

						break;
					case "premium":
						Role premiumRole = roleRepository.findByName(RoleEnum.ROLE_PREMIUM)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(premiumRole);

						break;
					case "user":
						Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			userToUpdate.setRoles(roles);
			User updatedUser = userService.save(userToUpdate);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "/user/")
	public @ResponseBody  ResponseEntity<Object> getUserById(@RequestParam Long id) {
		if(id == null || id == 0) {
			return new ResponseEntity<Object>("Missing ID", HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<User> user = userService.getById(id);
			if(user.isPresent()) {
				return new ResponseEntity<Object>(user.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("User not found", HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/user/{id}")
	public @ResponseBody ResponseEntity<String> deleteUser(@PathVariable Long id){
		try {
			userService.deleteById(id);
			return new ResponseEntity<String>("User deleted with ID " + id, HttpStatus.OK);
		} catch (EmptyResultDataAccessException exc) {
			return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	    	return new ResponseEntity<String>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}

}
