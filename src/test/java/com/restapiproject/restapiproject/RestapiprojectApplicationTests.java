package com.restapiproject.restapiproject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RestapiprojectApplicationTests {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Test
    public void loginTestSystem() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

        mockMvc
				.perform(get("http://localhost:8080/api/v1/employees").with(user("system").password("password").roles("ADMIN")))
				.andExpect(authenticated().withRoles("ADMIN"));

		mockMvc
				.perform(get("http://localhost:8080/api/v1/employees").with(user("user").password("password").roles("GENERAL")))
				.andExpect(authenticated().withRoles("GENERAL"));

		mockMvc
				.perform(put("http://localhost:8080/api/v1/employees/1").with(user("system").password("password").roles("ADMIN")))
				.andExpect(authenticated().withRoles("ADMIN"));

		mockMvc
				.perform(put("http://localhost:8080/api/v1/employees/1").with(user("user").password("password").roles("GENERAL")))
				.andExpect(authenticated().withRoles("GENERAL"));

		mockMvc
				.perform(post("http://localhost:8080/api/v1/employees/2").with(user("system").password("password").roles("ADMIN")))
				.andExpect(authenticated().withRoles("ADMIN"));

		mockMvc
				.perform(post("http://localhost:8080/api/v1/employees/2").with(user("system").password("password").roles("GENERAL")))
				.andExpect(authenticated().withRoles("GENERAL"));

		mockMvc
				.perform(delete("http://localhost:8080/api/v1/employees/3").with(user("system").password("password").roles("ADMIN")))
				.andExpect(status().isOk());

		mockMvc
				.perform(delete("http://localhost:8080/api/v1/employees/3").with(user("system").password("password").roles("GENERAL")))
				.andExpect(authenticated().withRoles("GENERAL"));
    }

}
