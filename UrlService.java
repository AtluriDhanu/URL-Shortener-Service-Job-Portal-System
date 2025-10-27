package com.url_shortener.service;

import com.url_shortener.entity.Url;

public interface UrlService {
	Url generateShortUrl(String originalUrl, int maxClicks, int durationDays);
	String redirectToOriginalUrl(String Hash);
}
