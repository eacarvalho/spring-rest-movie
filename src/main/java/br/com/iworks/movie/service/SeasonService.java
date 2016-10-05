package br.com.iworks.movie.service;

import br.com.iworks.movie.model.entity.Season;

public interface SeasonService {

    Season create(Season season);
    Season update(String title, Integer seasonNo, Season season);
}