package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.config.JsonConfiguration;
import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.util.JsonHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OmdbApiServiceImplTest {

    @InjectMocks
    private OmdbApiServiceImpl service;

    @Mock
    private OmdbApiGateway omdbApiGateway;

    private JsonHelper jsonHelper;

    @Before
    public void setup() {
        jsonHelper = new JsonHelper(new JsonConfiguration().getObjectMapper());
    }

    @Test
    public void successFindMovieByImdbID() throws Exception {
        OmdbApiResource mockOmdbApiResource = this.getOmdbApiResource();
        String imdbID = "tt3032476";

        when(omdbApiGateway.findByImdbID(imdbID)).thenReturn(mockOmdbApiResource);

        OmdbApiResource resource = service.findMovie(imdbID, null, null);

        verify(omdbApiGateway, times(1)).findByImdbID(imdbID);
        verify(omdbApiGateway, never()).findByTitleAndYear(anyString(), anyString());
        verify(omdbApiGateway, never()).findByTitle(anyString());
        assertResourceApi(resource);
    }

    @Test
    public void successFindMovieByTitleAndYear() throws Exception {
        OmdbApiResource mockOmdbApiResource = this.getOmdbApiResource();
        String title = "Better Call Saul";
        String year = "2015";

        when(omdbApiGateway.findByTitleAndYear(title.toLowerCase(), year)).thenReturn(mockOmdbApiResource);

        OmdbApiResource resource = service.findMovie(null, title, year);

        verify(omdbApiGateway, never()).findByImdbID(anyString());
        verify(omdbApiGateway, times(1)).findByTitleAndYear(title.toLowerCase(), year);
        verify(omdbApiGateway, never()).findByTitle(anyString());
        assertResourceApi(resource);
    }

    @Test
    public void successFindMovieByTitle() throws Exception {
        OmdbApiResource mockOmdbApiResource = this.getOmdbApiResource();
        String title = "Better Call Saul";

        when(omdbApiGateway.findByTitle(title.toLowerCase())).thenReturn(mockOmdbApiResource);

        OmdbApiResource resource = service.findMovie(null, title, null);

        verify(omdbApiGateway, never()).findByImdbID(anyString());
        verify(omdbApiGateway, never()).findByTitleAndYear(anyString(), anyString());
        verify(omdbApiGateway, times(1)).findByTitle(title.toLowerCase());
        assertResourceApi(resource);
    }

    @Test
    public void returnNullFindMovieByTitle() throws Exception {
        String title = "Better Call Saul";

        when(omdbApiGateway.findByTitle(title.toLowerCase())).thenReturn(null);

        OmdbApiResource resource = service.findMovie(null, title, null);

        verify(omdbApiGateway, never()).findByImdbID(anyString());
        verify(omdbApiGateway, never()).findByTitleAndYear(anyString(), anyString());
        verify(omdbApiGateway, times(1)).findByTitle(title.toLowerCase());
        assertNull(resource);
    }

    @Test
    public void successFindSeasonByTitleAndSeason() throws Exception {
        OmdbApiSeasonResource mockOmdbApiSeasonResource = this.getOmdbApiSeasonResource();
        String title = "Better Call Saul";
        Integer season = 1;

        when(omdbApiGateway.findSerieByTitleAndSeason(title.toLowerCase(), season)).thenReturn(mockOmdbApiSeasonResource);

        OmdbApiSeasonResource resource = service.findSeason(title, season);

        verify(omdbApiGateway, times(1)).findSerieByTitleAndSeason(title.toLowerCase(), season);
        assertThat(resource.getTitle(), is("Better Call Saul"));
        assertThat(resource.getSeason(), is("1"));
        assertThat(resource.getTotalSeasons(), is("3"));
        assertThat(resource.getEpisodes().get(0).getTitle(), is("Uno"));
        assertThat(resource.getEpisodes().get(0).getReleased(), is("2015-02-08"));
        assertThat(resource.getEpisodes().get(0).getEpisode(), is(1));
        assertThat(resource.getEpisodes().get(0).getImdbRating(), is("8.7"));
        assertThat(resource.getEpisodes().get(0).getImdbID(), is("tt3464768"));
    }

    @Test
    public void returnNullFindSeasonByTitleAndSeason() throws Exception {
        String title = "Better Call Saul";
        Integer season = 1;

        when(omdbApiGateway.findSerieByTitleAndSeason(title.toLowerCase(), season)).thenReturn(null);

        OmdbApiSeasonResource resource = service.findSeason(title, season);

        verify(omdbApiGateway, times(1)).findSerieByTitleAndSeason(title.toLowerCase(), season);
        assertNull(resource);
    }

    private void assertResourceApi(OmdbApiResource resource) {
        assertThat(resource.getTitle(), is("Better Call Saul"));
        assertThat(resource.getYear(), is("2015"));
        assertThat(resource.getReleased(), is("08 Feb 2015"));
        assertThat(resource.getRuntime(), is("46 min"));
        assertThat(resource.getGenre(), is("Crime, Drama"));
        assertThat(resource.getDirector(), is("N/A"));
        assertThat(resource.getWriter(), is("Vince Gilligan, Peter Gould"));
        assertThat(resource.getActors(), is("Bob Odenkirk, Jonathan Banks, Rhea Seehorn, Michael McKean"));
        assertThat(resource.getPlot(), is("The trials and tribulations of criminal lawyer, Jimmy McGill, in the time leading up to establishing his strip-mall law office in Albuquerque, New Mexico."));
        assertThat(resource.getLanguage(), is("English, Spanish, Vietnamese"));
        assertThat(resource.getPoster(), is("http://ia.media-imdb.com/images/M/MV5BNjk5MjYwNjg4NV5BMl5BanBnXkFtZTgwNzAzMzc5NzE@._V1_SX300.jpg"));
        assertThat(resource.getImdbRating(), is("8.8"));
        assertThat(resource.getImdbID(), is("tt3032476"));
        assertThat(resource.getImdbVotes(), is("154,458"));
        assertThat(resource.getType(), is("series"));
        assertThat(resource.getTotalSeasons(), is("3"));
    }

    private OmdbApiResource getOmdbApiResource() throws Exception {
        return jsonHelper.fromJson(readJson("json/omdb-movie.json"), OmdbApiResource.class);
    }

    private OmdbApiSeasonResource getOmdbApiSeasonResource() throws Exception {
        return jsonHelper.fromJson(readJson("json/omdb-season.json"), OmdbApiSeasonResource.class);
    }

    private String readJson(String path) throws IOException, URISyntaxException {
        URL invoiceURL = this.getClass().getClassLoader().getResource(path);
        byte[] encoded = Files.readAllBytes(Paths.get(invoiceURL.toURI()));
        return new String(encoded, "UTF-8");
    }
}