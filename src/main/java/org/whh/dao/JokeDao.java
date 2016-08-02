package org.whh.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Joke;

public interface JokeDao extends PagingAndSortingRepository<Joke, Long> {
	Joke findByContent(String content);
}