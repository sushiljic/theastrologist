package com.theastrologist.data.repository;

import com.theastrologist.domain.user.User;
import org.springframework.data.repository.CrudRepository;

public interface IndividualRepository extends CrudRepository<User, Long> {
}
