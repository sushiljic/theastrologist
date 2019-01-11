package com.theastrologist.controller;

import com.theastrologist.controller.exception.NoResultsFoundException;
import com.theastrologist.domain.individual.Individual;
import com.theastrologist.domain.user.User;
import com.theastrologist.service.IndividualService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/user/{userName}/individual", tags = "Individuals", description = "Individuals")
public class IndividualController extends AbstractController {
	@Autowired
	private IndividualService individualService;

	@ApiOperation(value = "Find individual by name", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Individual not found"),
			@ApiResponse(code = 404, message = "Too many individuals found")
	})
	@GetMapping(value = "/{individualName}")
	public Individual getIndividual(
			@ApiParam(value = "User Name", required = true) @PathVariable String userName,
			@ApiParam(value = "Individual Name", required = true) @PathVariable String individualName) throws NoResultsFoundException {
		User user = getUser(userName);

		Individual individual = individualService.findIndividualByName(individualName);
		if(individual == null) {
			throw new NoResultsFoundException();
		}
		return individual;
	}
}
