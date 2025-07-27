package Url_shortner.url_shortner.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import Url_shortner.url_shortner.DTO.StatResponse;
import Url_shortner.url_shortner.DTO.UrlRequest;
import Url_shortner.url_shortner.entity.Url;
import Url_shortner.url_shortner.exception.CustomAliasAlreadyInUseException;
import Url_shortner.url_shortner.exception.InvalidUrlException;
import Url_shortner.url_shortner.exception.UrlExpiredException;
import Url_shortner.url_shortner.exception.UrlNotFoundException;
import Url_shortner.url_shortner.repository.UrlRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UrlShorteningService {
	private UrlRepository urlRepository;
	private  UrlValidator urlValidator;
    private BaseConverter convertor;
    @Value("${app.short-url-domain}")
    private String shortUrlDomain;
    private static final Logger logger = LoggerFactory.getLogger(UrlShorteningService.class);
    
    @Autowired
    public UrlShorteningService(UrlRepository urlRepository, BaseConverter convertor) {
        this.urlRepository = urlRepository;
        this.convertor = convertor;
        this.urlValidator = new UrlValidator(new String[]{"http", "https"});
    }



    
	public void validateUrl(String url) {
		if(!urlValidator.isValid(url)) {
			throw new InvalidUrlException("URL Entered Is Not A Valid Http or Https URL");
		}

	}
	
	public Optional<Url> getByOriginalUrl(String originalUrl){
		return urlRepository.findByOriginalUrl(originalUrl);
	}
	
	public String getDomainToUse(String domain) {

		if (domain != null && !domain.isEmpty()) {
	    	return "https://" + domain;
	    } 
	    return shortUrlDomain;
	}
	
	public String getShortCodeToUse(String shortCode) {
		if (shortCode == null || shortCode.isEmpty()) {
		    do {
		    	shortCode = convertor.generateShortUrl();
	        } while (urlRepository.findByShortUrl(shortCode).isPresent()); 
	        
		    return shortCode;
		   
		}
	    else {
	    	if (urlRepository.findByShortUrl(shortCode) != null) {
	            throw new CustomAliasAlreadyInUseException("Custom alias '" + shortCode + "' already in use.");
	        }
	    }
		return shortCode;
	}
	
    @Transactional
	public String convertUrl(UrlRequest urlRequest) {
		String originalUrl = urlRequest.getOriginalUrl();
		validateUrl(originalUrl);
			
		Optional<Url> existing = getByOriginalUrl(originalUrl);
		if (existing.isPresent()) {
		    return  existing.get().getDomain()+"/"+existing.get().getShortUrl();
		}

		Url newUrl = new Url();
	    newUrl.setOriginalUrl(originalUrl);
	    newUrl.setCreatedAt(LocalDateTime.now());
	    newUrl.setExpiryDate(LocalDateTime.now().plusDays(30));
	    String domainToUse = getDomainToUse(urlRequest.getDomain());
	    String shortCode = getShortCodeToUse(urlRequest.getCustomAlais());
	    newUrl.setDomain(domainToUse);
	    newUrl.setShortUrl(shortCode);
	    urlRepository.save(newUrl); 
	    return domainToUse + "/" + shortCode;
	}
	
	
    @Transactional
	public ResponseEntity<?> redirectUrl(String shortUrl) {
    	 Url redirectUrl = urlRepository.findByShortUrl(shortUrl)
                 .orElseThrow(() -> new UrlNotFoundException("Short URL '" + shortUrl + "' not found"));
		
		
			
		if (redirectUrl.getExpiryDate().isBefore(LocalDateTime.now())) {
			  deleteExpiredUrl();
		      throw new UrlExpiredException("This URL has expired.");
		      
		    }
		
		redirectUrl.setClickCount(redirectUrl.getClickCount() + 1);
		urlRepository.save(redirectUrl);
		
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.location(URI
						.create(redirectUrl
						.getOriginalUrl()))
				.build();
	}



	public StatResponse getStatistics(String shortUrl) {
		
		Url stat = urlRepository.findByShortUrl(shortUrl)
                 .orElseThrow(() -> new UrlNotFoundException("Short URL '" + shortUrl + "' not found"));
		if(stat == null) {
			throw new UrlNotFoundException("Short URL '" + shortUrl + "' not found");
		    }
		
		
		return new StatResponse(
				stat.getOriginalUrl(),
				stat.getDomain(),
				stat.getShortUrl(),
				stat.getCreatedAt(),
				stat.getExpiryDate(),
				stat.getClickCount());
				
	}
	
	
	
	@Scheduled(cron = "0 0 0 * * *") 
	public void deleteExpiredUrl() {
		try {
		    List<Url> expired = urlRepository.findByExpiryDateBefore(LocalDateTime.now());
		    if(expired != null) {
		    	logger.info("Deleting " + expired.size() + " expired url");
			    urlRepository.deleteAll(expired);
			    logger.info("Expired URLs are deleted successfully " );
		    }
		    else {
		    	logger.info("No expired URLs are found" );
		    }
		    }
		catch(Exception e) {
			logger.error("Error during scheduled cleanup of expired URLs: {}", e.getMessage(), e);
		}

	}

}
