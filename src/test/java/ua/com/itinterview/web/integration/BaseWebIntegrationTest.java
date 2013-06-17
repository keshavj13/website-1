package ua.com.itinterview.web.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.itinterview.web.security.AuthenticationUtils;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-web-context.xml" })
public abstract class BaseWebIntegrationTest extends
	AbstractTransactionalJUnit4SpringContextTests {

    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Before
    public void setUp() {
	mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected MockHttpServletRequestBuilder registerUser(String userName,
	    String name, String email, String password, String confirmPassword) {
	return post("/register").param("userName", userName)
		.param("name", name).param("email", email)
		.param("password", password)
		.param("confirmPassword", confirmPassword);
    }

    protected MockHttpServletRequestBuilder loginUser(String userName,
	    String password) {
	return post("/j_spring_security_check").param("j_username", userName)
		.param("j_password", password);
    }

    @After
    public void logout() {
	SecurityContextHolder.getContext().setAuthentication(null);
    }
}