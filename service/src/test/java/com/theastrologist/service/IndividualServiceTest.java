package com.theastrologist.service;

import com.theastrologist.ServiceTestConfiguration;
import com.theastrologist.data.repository.IndividualRepository;
import com.theastrologist.domain.individual.Individual;
import com.theastrologist.domain.user.User;
import com.theastrologist.exception.TooManyResultsException;
import com.theastrologist.exception.UserAlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceTestConfiguration.class})
public class IndividualServiceTest {
	@MockBean
	private IndividualRepository repository;

	@Autowired
	private IndividualService service;

	@Autowired
	private UserService userService;

	@Test
	public void findByName() throws TooManyResultsException, UserAlreadyExistsException {
		String username = "Alex";
		User user = new User(username);

		userService.createUser(user);

		String name = "Robert";
		Individual individual = new Individual(name);

		List<Individual> individuals = newArrayList();
		individuals.add(individual);

		Mockito.when(repository.findDistinctIndividualByUserOrName(user, name)).thenReturn(individuals);

		Individual found = service.findIndividualByName(user, name);
		assertThat(found.getName()).isEqualTo(name);
	}
}