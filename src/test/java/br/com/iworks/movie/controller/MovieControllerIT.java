package br.com.iworks.movie.controller;

import static br.com.iworks.movie.util.JsonHelper.fromJson;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.iworks.movie.model.resource.MovieResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerIT {

    private final static String API_MOVIE = "/api/movies";
    private final static String API_MOVIE_BY_CODE = "/api/movies/{code}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_WhenPayloadContainsImdbOrTitleIsCorrect_ShouldCreateMovieAndStoreInDatabase() throws Exception {
        String requestBody = "{\"originalTitle\":\"Batman\",\"rating\":4,\"imdbID\":\"tt0096895\"}";

        this.mockMvc
                .perform(post(API_MOVIE).contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.title", is("Batman")))
                .andExpect(jsonPath("$.originalTitle", is("Batman")))
                .andExpect(jsonPath("$.duration", is("126 min")))
                .andExpect(jsonPath("$.type", is("MOVIE")))
                .andExpect(jsonPath("$.genres", hasSize(2)))
                .andExpect(jsonPath("$.releasedDate", is("1989-06-23")))
                .andExpect(jsonPath("$.year", is("1989")))
                .andExpect(jsonPath("$.plot", is("The Dark Knight of Gotham City begins his war on crime with his first major enemy being Jack Napier, a criminal who becomes the clownishly homicidal Joker.")))
                .andExpect(jsonPath("$.director", is("Tim Burton")))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.imdbRating", is("7.5")))
                .andExpect(jsonPath("$.imdbID", is("tt0096895")))
                .andExpect(jsonPath("$.poster", is("https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg")));
    }

    @Test
    void create_WhenPayloadToCreateMovieIsInvalid_ShouldThrowException() throws Exception {
        String requestBody = "{\"originalTitle\":\"Batman\",\"rating\":7}";

        this.mockMvc
                .perform(post(API_MOVIE).contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Validation Failed")))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(jsonPath("$.details", hasItem("Rating must be between 0 and 5")));
    }

    @Test
    void update_WhenTheMovieCodeExists_ShouldUpdateMovieAndStoreInDatabase() throws Exception {
        String requestBody = "{\"imdbID\":\"tt0936501\",\"rating\":0}";

        MvcResult movieCreated = this.mockMvc.perform(post(API_MOVIE).contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        MovieResource movieResource = fromJson(movieCreated.getResponse().getContentAsString(), MovieResource.class);

        String updateRequestBody = "{\"code\":1,\"title\":\"Internal new title\",\"originalTitle\":\"Taken\",\"rating\":3,\"imdbRating\":\"7.8\",\"imdbID\":\"tt0936501\"}";

        this.mockMvc
                .perform(put(API_MOVIE_BY_CODE, movieResource.getCode()).contentType(APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(movieResource.getCode()), Long.class))
                .andExpect(jsonPath("$.title", is("Internal new title")))
                .andExpect(jsonPath("$.originalTitle", is("Taken")))
                .andExpect(jsonPath("$.duration", is("90 min")))
                .andExpect(jsonPath("$.type", is("MOVIE")))
                .andExpect(jsonPath("$.genres", hasSize(3)))
                .andExpect(jsonPath("$.releasedDate", is("2009-01-30")))
                .andExpect(jsonPath("$.year", is("2008")))
                .andExpect(jsonPath("$.plot", is("A retired CIA agent travels across Europe and relies on his old skills to save his estranged daughter, who has been kidnapped while on a trip to Paris.")))
                .andExpect(jsonPath("$.director", is("Pierre Morel")))
                .andExpect(jsonPath("$.rating", is(3)))
                .andExpect(jsonPath("$.imdbRating", is("7.8")))
                .andExpect(jsonPath("$.imdbID", is("tt0936501")))
                .andExpect(jsonPath("$.poster", is("https://m.media-amazon.com/images/M/MV5BMTM4NzQ0OTYyOF5BMl5BanBnXkFtZTcwMDkyNjQyMg@@._V1_SX300.jpg")));
    }

    @Test
    void update_WhenTheMovieCodeExistsAndAccessImdbIsFalse_ShouldUpdateMovieAndStoreInDatabase() throws Exception {
        String requestBody = "{\"imdbID\":\"tt0083944\",\"rating\":0}";

        MvcResult movieCreated = this.mockMvc.perform(post(API_MOVIE).contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        MovieResource movieResource = fromJson(movieCreated.getResponse().getContentAsString(), MovieResource.class);

        String updateRequestBody = "{\"title\":\"Rambo\",\"originalTitle\":\"First Blood\",\"duration\":\"93 min\",\"type\":\"MOVIE\",\"genres\":[\"ACTION\",\"ADVENTURE\",\"THRILLER\"],\"releasedDate\":\"1982-10-22\",\"year\":\"1982\",\"plot\":\"A veteran Green Beret\",\"director\":\"Ted Kotcheff\",\"rating\":2,\"imdbRating\":\"7.7\"}";

        this.mockMvc
                .perform(put(API_MOVIE_BY_CODE + "?accessImdb=false", movieResource.getCode()).contentType(APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(movieResource.getCode()), Long.class))
                .andExpect(jsonPath("$.title", is("Rambo")))
                .andExpect(jsonPath("$.originalTitle", is("First Blood")))
                .andExpect(jsonPath("$.duration", is("93 min")))
                .andExpect(jsonPath("$.type", is("MOVIE")))
                .andExpect(jsonPath("$.genres", hasSize(3)))
                .andExpect(jsonPath("$.releasedDate", is("1982-10-22")))
                .andExpect(jsonPath("$.year", is("1982")))
                .andExpect(jsonPath("$.plot", is("A veteran Green Beret")))
                .andExpect(jsonPath("$.director", is("Ted Kotcheff")))
                .andExpect(jsonPath("$.rating", is(2)))
                .andExpect(jsonPath("$.imdbRating", is("7.7")))
                .andExpect(jsonPath("$.imdbID", is(nullValue())))
                .andExpect(jsonPath("$.poster", is(nullValue())));
    }

    @Test
    void update_WhenTheMovieCodeDoesNotExistInTheDatabase_ShouldReturnNotFoundException() throws Exception {
        String updateRequestBody = "{\"title\":\"Rambo\",\"originalTitle\":\"First Blood\",\"rating\":0}";

        this.mockMvc.perform(put(API_MOVIE_BY_CODE, 555).contentType(APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Movie not found")));
    }

    @Test
    void find_WhenTheMovieCodeExists_ShouldReturnTheMovieStoredInDatabase() throws Exception {
        String requestBody = "{\"originalTitle\":\"Taken 3\",\"rating\":5}";

        MvcResult movieCreated = this.mockMvc.perform(post(API_MOVIE).contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        MovieResource movieResource = fromJson(movieCreated.getResponse().getContentAsString(), MovieResource.class);

        this.mockMvc.perform(get(API_MOVIE_BY_CODE, movieResource.getCode()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(movieResource.getCode()), Long.class))
                .andExpect(jsonPath("$.title", is("Taken 3")))
                .andExpect(jsonPath("$.originalTitle", is("Taken 3")))
                .andExpect(jsonPath("$.duration", is("108 min")))
                .andExpect(jsonPath("$.type", is("MOVIE")))
                .andExpect(jsonPath("$.genres", hasSize(3)))
                .andExpect(jsonPath("$.releasedDate", is("2015-01-09")))
                .andExpect(jsonPath("$.year", is("2014")))
                .andExpect(jsonPath("$.plot", is("Accused of a ruthless murder he never committed or witnessed, Bryan Mills goes on the run and brings out his particular set of skills to find the true killer and clear his name.")))
                .andExpect(jsonPath("$.director", is("Olivier Megaton")))
                .andExpect(jsonPath("$.rating", is(5)))
                .andExpect(jsonPath("$.imdbRating", is("6.0")))
                .andExpect(jsonPath("$.imdbID", is("tt2446042")))
                .andExpect(jsonPath("$.poster", is("https://m.media-amazon.com/images/M/MV5BNjM5MDU3NTY0M15BMl5BanBnXkFtZTgwOTk2ODU2MzE@._V1_SX300.jpg")));
    }

    @Test
    void find_WhenTheMovieCodeDoesNotExistInTheDatabase_ShouldReturnNotFoundException() throws Exception {
        this.mockMvc.perform(get(API_MOVIE_BY_CODE, 555).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Movie not found")));
    }

    @Test
    void delete_WhenTheMovieCodeExists_ShouldDeleteTheMovieStoredInDatabase() throws Exception {
        String requestBody = "{\"originalTitle\":\"Taken 2\",\"rating\":5}";

        MvcResult movieCreated = this.mockMvc.perform(post(API_MOVIE).contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        MovieResource movieResource = fromJson(movieCreated.getResponse().getContentAsString(), MovieResource.class);

        this.mockMvc.perform(delete(API_MOVIE_BY_CODE, movieResource.getCode()).contentType(APPLICATION_JSON))
                .andExpect(content().string(""))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void delete_WhenTheMovieCodeDoesNotExistInDatabase_ShouldReturnNotFoundException() throws Exception {
        this.mockMvc.perform(delete(API_MOVIE_BY_CODE, 555).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Movie not found")));
    }
}