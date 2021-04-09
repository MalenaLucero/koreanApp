package com.koreanApp.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreanApp.entity.Role;
import com.koreanApp.entity.User;
import com.koreanApp.enums.RoleEnum;
import com.koreanApp.payload.SetRolesRequest;
import com.koreanApp.repository.RoleRepository;
import com.koreanApp.repository.UserRepository;
import com.koreanApp.service.UserService;

@Controller
@RequestMapping(path = "/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
			User updatedUser = userRepository.save(userToUpdate);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
		}
	}

}
