package com.epam.newsmanagement.admin.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * testing {@link LoginController}
 */
public class LoginControllerTest {

    /**
     * testiog method loginView()
     * @throws Exception
     */
    @Test
    public void testLoginView() throws Exception{
        LoginController controller = new LoginController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(view().name("login"));
        mockMvc.perform(post("/"))
                .andExpect(view().name("login"));
    }

}
