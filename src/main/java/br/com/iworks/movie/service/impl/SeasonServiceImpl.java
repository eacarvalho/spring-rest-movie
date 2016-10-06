package br.com.iworks.movie.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.base.CaseFormat;

import br.com.iworks.movie.exceptions.ConflictException;
import br.com.iworks.movie.exceptions.MovieException;
import br.com.iworks.movie.model.entity.Season;
import br.com.iworks.movie.repository.SeasonRepository;
import br.com.iworks.movie.service.SeasonService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository repo;

    @Autowired
    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Season create(Season season) {
        try {
            this.validateSeason(season);

            season.setId(this.generateId(season.getTitle(), season.getNumber()));

            season = repo.save(season);
        } catch (Exception e) {
            throw movieException(season, e);
        }

        return season;
    }

    @Override
    public Season update(String title, Integer number, Season season) {
        Season savedSeason = repo.findOne(this.generateId(title, number));

        if (savedSeason != null) {
            try {
                this.validateSeason(season);

                season.setId(savedSeason.getId());

                repo.save(season);

                return season;
            } catch (Exception e) {
                throw movieException(season, e);
            }
        }

        return null;
    }

    @Override
    public Page<Season> list(String title, Pageable pageable) {
        return repo.findByTitleIgnoreCase(title.trim(), pageable);
    }

    @Override
    public Page<Season> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Season read(String title, Integer number) {
        return repo.findOne(generateId(title, number));
    }

    @Override
    public Season delete(String title, Integer number) {
        Season season = this.read(title, number);

        if (season != null) {
            repo.delete(season);

            return season;
        }

        return null;
    }

    private void validateSeason(Season season) {
        Set<ConstraintViolation<Season>> errors = validator.validate(season);

        if (!CollectionUtils.isEmpty(errors)) {
            String error = errors.stream().map(err -> err.getPropertyPath() + " : " + err.getMessage()).collect(Collectors.joining(" | "));
            throw new MovieException(error);
        }
    }

    private MovieException movieException(Season season, Exception ex) {
        String msgError = messageSource.getMessage("movie.save.error", new Object[]{season.getTitle(), ex.getMessage()}, LocaleContextHolder.getLocale());

        if (DuplicateKeyException.class.isInstance(ex)) {
            return new ConflictException(msgError);
        } else {
            return new MovieException(msgError);
        }
    }

    private String generateId(final String originalTitle, final Integer number) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, originalTitle.toLowerCase())
                .concat("-")
                .concat(number.toString())
                .replaceAll(" ", "-")
                .trim();
    }
}