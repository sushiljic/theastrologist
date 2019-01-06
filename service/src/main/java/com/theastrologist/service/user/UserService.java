package com.theastrologist.service.user;

import com.theastrologist.data.service.UserDataService;
import com.theastrologist.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDataService userDataService;

    public User getUser(String username){
        return userDataService.getUserByName(username);
    }
}
