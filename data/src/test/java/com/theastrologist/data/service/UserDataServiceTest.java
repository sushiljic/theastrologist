package com.theastrologist.data.service;

import com.theastrologist.data.repository.UserRepository;
import com.theastrologist.domain.user.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class UserDataServiceTest {
    @TestConfiguration
    static class UserDataServiceTestContextConfiguration {

        @Bean
        public UserDataService employeeService() {
            return new UserDataService();
        }
    }

    @Autowired
    UserDataService userDataService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setup() {
        User alex = new User("alex");

        Mockito.when(userRepository.findByUserName(alex.getUserName())).thenReturn(alex);
    }


    @Test
    public void findByName() {
        String name = "alex";
        User found = userDataService.getUserByName(name);
        assertThat(found.getUserName()).isEqualTo(name);
    }

    @Test
    @Ignore
    public void getAllArticlesTest() {
        // when

        List<User> userList = userDataService.getUsers();
        assertThat(userList).isNotNull();

        User user = userList.get(0);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUserName()).isEqualTo("Choupi");

        User userTwo = userList.get(1);
        assertThat(userTwo.getId()).isEqualTo(2L);
        assertThat(userTwo.getUserName()).isEqualTo("Maybe for fun");
    }
}