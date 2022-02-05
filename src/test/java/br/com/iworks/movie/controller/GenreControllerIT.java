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
class GenreControllerIT {

    private final static String API_GENRE = "/api/genres";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll_WhenRetrievingMovieGenre_ShouldReturnAllGenres() throws Exception {
        this.mockMvc
            .perform(get(API_GENRE).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(22)))
            .andExpect(jsonPath("$", hasItem("ACTION")))
            .andExpect(jsonPath("$", hasItem("ADVENTURE")))
            .andExpect(jsonPath("$", hasItem("ANIMATION")))
            .andExpect(jsonPath("$", hasItem("BIOGRAPHY")))
            .andExpect(jsonPath("$", hasItem("COMEDY")))
            .andExpect(jsonPath("$", hasItem("CRIME")))
            .andExpect(jsonPath("$", hasItem("DOCUMENTARY")))
            .andExpect(jsonPath("$", hasItem("DRAMA")))
            .andExpect(jsonPath("$", hasItem("FAMILY")))
            .andExpect(jsonPath("$", hasItem("FANTASY")))
            .andExpect(jsonPath("$", hasItem("FILM_NOIR")))
            .andExpect(jsonPath("$", hasItem("HISTORY")))
            .andExpect(jsonPath("$", hasItem("HORROR")))
            .andExpect(jsonPath("$", hasItem("MUSIC")))
            .andExpect(jsonPath("$", hasItem("MUSICAL")))
            .andExpect(jsonPath("$", hasItem("MYSTERY")))
            .andExpect(jsonPath("$", hasItem("ROMANCE")))
            .andExpect(jsonPath("$", hasItem("SCI_FI")))
            .andExpect(jsonPath("$", hasItem("SPORT")))
            .andExpect(jsonPath("$", hasItem("THRILLER")))
            .andExpect(jsonPath("$", hasItem("WAR")))
            .andExpect(jsonPath("$", hasItem("WESTERN")));
    }
}