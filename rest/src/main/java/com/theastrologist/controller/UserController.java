package com.theastrologist.controller;

import com.theastrologist.controller.exception.NoResultsFoundException;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.domain.user.User;
import com.theastrologist.service.ThemeService;
import com.theastrologist.service.user.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get user by name", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Username not found")
    })
    @GetMapping(value = "/{username}")
    public User getUser(
            @ApiParam(value = "User Name", required = true) @PathVariable  String username) throws NoResultsFoundException {
        User user = userService.getUser(username);
        if(user == null) {
            throw new NoResultsFoundException();
        }
        return user;
    }
}
