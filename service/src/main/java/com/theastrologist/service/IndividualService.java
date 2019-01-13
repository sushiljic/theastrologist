package com.theastrologist.service;

import com.theastrologist.data.repository.IndividualRepository;
import com.theastrologist.domain.individual.Individual;
import com.theastrologist.domain.user.User;
import com.theastrologist.exception.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividualService {

	@Autowired
	private IndividualRepository individualRepository;

	public IndividualService() {}

	public Individual findIndividualByName(User user, String individualName) throws TooManyResultsException {
		List<Individual> individuals = individualRepository.findDistinctIndividualByUserOrName(user, individualName);

		if(individuals.size() > 1) {
			throw new TooManyResultsException();
		}

		return individuals.get(0);
	}
}
