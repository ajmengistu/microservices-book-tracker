package com.company.userservice.controller;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.userservice.UserServiceApplication;
import com.company.userservice.controller.vm.UserVM;
import com.company.userservice.repository.UserRepository;
import com.company.userservice.utils.TestUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link UserAccountResourceController}.
 */
@SpringBootTest(classes = UserServiceApplication.class)
@AutoConfigureMockMvc
public class UserAccountResourceControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc accountControllerMockMvc;

    @Test
    @Transactional
    public void testRegisterValid() throws Exception {
        UserVM validUser = new UserVM();
        String username = "test-register-valid-username";
        validUser.setPassword("password");
        validUser.setUsername(username);
        assertThat(userRepository.findOneByUsername(username).isPresent()).isFalse();

        accountControllerMockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(validUser))).andExpect(status().isCreated());

        assertThat(userRepository.findOneByUsername(username).isPresent()).isTrue();
    }

}