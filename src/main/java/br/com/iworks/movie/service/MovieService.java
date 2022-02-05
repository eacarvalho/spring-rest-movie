package br.com.iworks.movie.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import br.com.iworks.movie.exception.ResourceNotFoundException;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.model.resource.MovieResource;
import br.com.iworks.movie.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.repository.MovieRepository;
import br.com.iworks.movie.transformer.MovieResourceMapper;
import br.com.iworks.movie.transformer.OmdbApiResourceMapper;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@AllArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final CounterService counterService;
    private final OmdbService omdbService;
    private final OmdbApiResourceMapper omdbApiResourceMapper;
    private final MovieResourceMapper movieResourceMapper;

    public Movie create(MovieResource resource, boolean accessImdb) {
        Movie movie = this.searchOmdbMovie(resource, accessImdb);

        movie.setCode(counterService.getNextSequence(Movie.COLLECTION_NAME));
        movie.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));

        return movieRepository.save(movie);
    }

    public Movie update(Long code, MovieResource resource, boolean accessImdb) {
        Movie savedMovie = this.find(code);
        Movie movie = this.searchOmdbMovie(resource, accessImdb);

        movie.setId(savedMovie.getId());
        movie.setCode(savedMovie.getCode());

        return movieRepository.save(movie);
    }

    public Movie find(Long code) {
        return movieRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    public void delete(Long code) {
        movieRepository.delete(movieRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found")));
    }

    private Movie searchOmdbMovie(MovieResource resource, boolean accessImdb) {
        Movie movie = null;

        if (accessImdb) {
            OmdbApiResource omdbApiResource = omdbService.findMovie(resource.getImdbID(),
                    resource.getOriginalTitle(),
                    resource.getYear());

            movie = omdbApiResourceMapper.toModel(omdbApiResource);
        } else {
            movie = movieResourceMapper.toModel(resource);
        }

        if (!isEmpty(resource.getTitle())) {
            movie.setTitle(resource.getTitle());
        }

        if (!isEmpty(resource.getPlot())) {
            movie.setPlot(resource.getPlot());
        }

        if (!isEmpty(resource.getPoster())) {
            movie.setPoster(resource.getPoster());
        }

        if (!CollectionUtils.isEmpty(resource.getGenres())) {
            List<GenreEnum> listGenre = new ArrayList<>(resource.getGenres());
            movie.setGenres(listGenre);
        }

        movie.setRating(resource.getRating());

        return movie;
    }
}