package com.url_shortener.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.url_shortener.entity.Url;
import com.url_shortener.repository.UrlRepository;


@Service
public class UrlServiceImp implements UrlService {

	@Autowired
	private UrlRepository urlRepository;
	
	@Value("${app.qr.path}")
	private String qrDirectory;

	public UrlServiceImp(UrlRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	@Override
	public Url generateShortUrl(String originalUrl, int maxClicks, int durationDays) {
		String hash = generateBase62Hash();
		String shortUrl = "http://localhost:8080/" + hash;
		
		Url url = new Url();
		url.setOriginalUrl(originalUrl);
		url.setShortUrl(shortUrl);
		url.setHash(hash);
		url.setMaxClicks(maxClicks);
		url.setExpiredAt(LocalDateTime.now().plusDays(durationDays));
		
		String qrPath = generateQRCode(shortUrl, hash);
		url.setQrCodePath(qrPath);
		return urlRepository.save(url);
	}

	private String generateBase62Hash() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i<8; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
			return sb.toString();
		}

	private String generateQRCode(String text, String fileName) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

            File file = new File(qrDirectory);
            if(!file.exists()) file.mkdirs();

            String filePath = qrDirectory + fileName + ".png";
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return "qr/" + fileName + ".png";
        } 
        catch(WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public String redirectToOriginalUrl(String Hash) {
		Url url = urlRepository.findByHash(Hash).orElse(null);
		if(url == null) {
			return "error";
		}
		if(url.getClickCount() >= url.getMaxClicks() || LocalDateTime.now().isAfter(url.getExpiredAt())) {
			return "expired";
		}
		url.setClickCount(url.getClickCount()+1);
		urlRepository.save(url);
		return url.getOriginalUrl();
	}
}	
