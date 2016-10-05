package br.com.iworks.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.iworks.movie.model.entity.Season;

public interface SeasonService {

    Season create(Season season);
    Season update(String title, Integer seasonNo, Season season);
    Page<Season> list(String title, Pageable pageable);
    Page<Season> list(Pageable pageable);
    Season read(String title, Integer number);
    Season delete(String title, Integer number);
}