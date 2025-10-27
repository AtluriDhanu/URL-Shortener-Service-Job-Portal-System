package com.url_shortener.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.url_shortener.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
	Optional<Url> findByHash(String Hash);
}
