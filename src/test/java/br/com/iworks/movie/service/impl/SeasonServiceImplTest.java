package br.com.iworks.movie.service.impl;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.Lists;

import br.com.iworks.movie.config.JsonConfiguration;
import br.com.iworks.movie.exceptions.ConflictException;
import br.com.iworks.movie.exceptions.MovieException;
import br.com.iworks.movie.model.entity.Season;
import br.com.iworks.movie.repository.SeasonRepository;
import br.com.iworks.movie.util.JsonHelper;

@RunWith(MockitoJUnitRunner.class)
public class SeasonServiceImplTest {

    @InjectMocks
    private SeasonServiceImpl service;

    @Mock
    private SeasonRepository repo;

    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;

    private JsonHelper jsonHelper;

    @Before
    public void setup() {
        jsonHelper = new JsonHelper(new JsonConfiguration().getObjectMapper());

        when(this.validator.validate(any())).thenAnswer(args -> {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator valid = factory.getValidator();
            return valid.validate(args.getArguments()[0]);
        });

        when(this.messageSource.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn(null);
    }

    @Test
    public void successCreateSeason() throws Exception {
        Season mockSeason = this.getSeason();

        assertNull(mockSeason.getId());
        when(repo.save(any(Season.class))).thenReturn(mockSeason);

        Season season = service.create(mockSeason);

        verify(repo, times(1)).save(any(Season.class));
        assertSeason(season);
    }

    @Test(expected = MovieException.class)
    public void errorRequiredNumberCreateSeason() {
        Season mockSeason = new Season();

        mockSeason.setTitle("Better Call Saul");

        service.create(mockSeason);

        verify(repo, never()).save(any(Season.class));
    }

    @Test(expected = ConflictException.class)
    public void errorDuplicateKeyCreateSeason() throws Exception {
        Season mockSeason = this.getSeason();

        when(repo.save(any(Season.class))).thenThrow(new DuplicateKeyException("409"));

        service.create(mockSeason);
    }

    @Test
    public void successUpdateSeason() throws Exception {
        Season mockSeason = this.getSeason();
        String id = "better-call-saul-1";

        assertNull(mockSeason.getId());

        mockSeason.setId(id);
        mockSeason.getEpisodes().get(0).setWatched(false);

        when(repo.findOne(id)).thenReturn(mockSeason);

        Season season = service.update("Better Call Saul", 1, mockSeason);

        verify(repo, times(1)).save(any(Season.class));
        assertThat(season.getId(), is("better-call-saul-1"));
        assertThat(season.getTitle(), is("Better Call Saul"));
        assertThat(season.getNumber(), is(1));
        assertThat(season.getTotalSeasons(), is("3"));
        assertThat(season.getEpisodes().size(), is(10));
        assertThat(season.getEpisodes().get(0).getTitle(), is("Uno"));
        assertThat(season.getEpisodes().get(0).getReleased(), is("2015-02-08"));
        assertThat(season.getEpisodes().get(0).getNumber(), is(1));
        assertThat(season.getEpisodes().get(0).getImdbRating(), is("8.7"));
        assertThat(season.getEpisodes().get(0).getImdbID(), is("tt3464768"));
        assertThat(season.getEpisodes().get(0).isWatched(), is(false));
    }

    @Test
    public void errorCodeNotFoundUpdateSeason() throws Exception {
        Season mockSeason = this.getSeason();
        String id = "better-call-saul-1";

        assertNull(mockSeason.getId());

        when(repo.findOne(id)).thenReturn(mockSeason);

        Season season = service.update("Better Call Saul", 2, mockSeason);

        verify(repo, never()).save(any(Season.class));
        assertNull(season);
        assertNotNull(mockSeason);
    }

    @Test(expected = MovieException.class)
    public void errorRequiredNumberUpdateMovie() throws Exception {
        Season mockSeason = this.getSeason();

        mockSeason.setNumber(null);

        when(repo.findOne(any(String.class))).thenReturn(mockSeason);

        service.update("Better Call Saul", 1, mockSeason);

        verify(repo, never()).save(any(Season.class));
    }

    @Test
    public void successDeleteSeason() throws Exception {
        Season mockSeason = this.getSeason();
        String id = "better-call-saul-1";

        mockSeason.setId(id);

        when(repo.findOne(id)).thenReturn(mockSeason);

        Season season = service.delete("Better Call Saul", 1);

        verify(repo, times(1)).delete(any(Season.class));
        assertSeason(season);
    }

    @Test
    public void errorDeleteSeason() {
        when(repo.findOne(any(String.class))).thenReturn(null);

        Season season = service.delete("Better Call Saul", 1);

        verify(repo, never()).delete(any(Season.class));
        assertNull(season);
    }

    @Test
    public void successListAll() throws Exception {
        Season mockSeason = this.getSeason();

        mockSeason.setId("better-call-saul-1");

        Pageable pageable = new PageRequest(1, 1);
        Page<Season> mockSeasons = new PageImpl<>(Lists.newArrayList(mockSeason), pageable, 1L);

        when(repo.findAll(any(Pageable.class))).thenReturn(mockSeasons);

        Page<Season> seasons = service.list(pageable);

        verify(repo, times(1)).findAll(any(Pageable.class));
        assertList(seasons);
    }

    @Test
    public void successListByFilter() throws Exception {
        Season mockSeason = this.getSeason();

        mockSeason.setId("better-call-saul-1");

        Pageable pageable = new PageRequest(1, 1);
        Page<Season> mockSeasons = new PageImpl<>(Lists.newArrayList(mockSeason), pageable, 1L);

        when(repo.findByTitleIgnoreCase(any(String.class), any(Pageable.class))).thenReturn(mockSeasons);

        Page<Season> seasons = service.list("Better Call Saul", pageable);

        verify(repo, times(1)).findByTitleIgnoreCase(any(String.class), any(Pageable.class));
        assertList(seasons);
    }

    private void assertSeason(Season season) {
        assertThat(season.getId(), is("better-call-saul-1"));
        assertThat(season.getTitle(), is("Better Call Saul"));
        assertThat(season.getNumber(), is(1));
        assertThat(season.getTotalSeasons(), is("3"));
        assertThat(season.getEpisodes().size(), is(10));
        assertThat(season.getEpisodes().get(0).getTitle(), is("Uno"));
        assertThat(season.getEpisodes().get(0).getReleased(), is("2015-02-08"));
        assertThat(season.getEpisodes().get(0).getNumber(), is(1));
        assertThat(season.getEpisodes().get(0).getImdbRating(), is("8.7"));
        assertThat(season.getEpisodes().get(0).getImdbID(), is("tt3464768"));
        assertThat(season.getEpisodes().get(0).isWatched(), is(true));
    }

    private void assertList(Page<Season> seasons) {
        assertNotNull(seasons);
        assertNotNull(seasons.getContent());
        assertSeason(seasons.getContent().get(0));
    }

    private Season getSeason() throws Exception {
        Season season = jsonHelper.fromJson(readJson(), Season.class);
        season.setNumber(1);
        season.setRating(5);
        return season;
    }

    private String readJson() throws IOException, URISyntaxException {
        URL invoiceURL = this.getClass().getClassLoader().getResource("json/season.json");
        byte[] encoded = Files.readAllBytes(Paths.get(invoiceURL.toURI()));
        return new String(encoded, "UTF-8");
    }
}