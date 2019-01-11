package com.theastrologist.controller;

import com.theastrologist.controller.exception.NoResultsFoundException;
import com.theastrologist.controller.exception.UserAlreadyExistsException;
import com.theastrologist.domain.user.User;
import com.theastrologist.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Get user by name", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Username not found")
	})
	@GetMapping(value = "/user/{username}")
	public User getUser(
			@ApiParam(value = "User Name", required = true) @PathVariable  String username) throws NoResultsFoundException {
		User user = userService.getUser(username);
		if(user == null) {
			throw new NoResultsFoundException();
		}
		return user;
	}

	@ApiOperation(value = "Create user", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "User created"),
			@ApiResponse(code = 400, message = "Username already exists")
	})
	@PostMapping(value = "/user")
	public ResponseEntity<Void> createUser(
			@ApiParam(value = "User", required = true) @RequestBody User user) throws UserAlreadyExistsException {
		try {
			userService.createUser(user);
		} catch(com.theastrologist.exception.UserAlreadyExistsException e) {
			throw new UserAlreadyExistsException();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userName}").buildAndExpand(user.getUserName()).toUri();

		return ResponseEntity.created(location).build();
	}
}
