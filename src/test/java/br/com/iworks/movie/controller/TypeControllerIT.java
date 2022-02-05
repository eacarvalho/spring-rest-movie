package br.com.iworks.movie.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TypeControllerIT {

    private final static String API_TYPE = "/api/types";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll_WhenRetrievingMovieType_ShouldReturnAllTypes() throws Exception {
        this.mockMvc
            .perform(get(API_TYPE).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(4)))
            .andExpect(jsonPath("$", hasItem("MOVIE")))
            .andExpect(jsonPath("$", hasItem("SERIE")))
            .andExpect(jsonPath("$", hasItem("EPISODE")))
            .andExpect(jsonPath("$", hasItem("CARTOONS")));
    }
}