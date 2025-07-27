package Url_shortner.url_shortner.entity;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique ID of the user", example = "1")
    private int id;

	@Column
	@Schema(description = "The orginal url given by user", example = "http://google.com/blog/why-you-should-use-a-url-shortener")
	private String originalUrl;
    
	@Column
	@Schema(description = "The shorterned url", example = "http://lazyurl.com/abc123")
	private String shortUrl;
	
	@Column
	@Schema(description = "Custom domain to be used for generating the short URL", example = "lazyurl.com")
	private String domain;

	@Column(nullable=false)
	@Schema(description = "The creation timestamp of the short URL", example = "2025-07-23T12:00:00")
	private LocalDateTime createdAt;
    
	@Column(nullable=false)
	@Schema(description = "The expiry timestamp of the short URL", example = "2025-08-23T12:00:00")
	private LocalDateTime expiryDate;
	
	@Column
	@Schema(description = "Total number of times the short URL has been accessed", example = "7")
    private int clickCount;
	

	
	public Url() {
		
	}

	
    
    public Url(String originalUrl, String shortUrl) {
		
		this.originalUrl = originalUrl;
		this.shortUrl = shortUrl;
		this.clickCount = 0;
		
	}
    
    
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDomain() {
		return domain;
	}



	public void setDomain(String domain) {
		this.domain = domain;
	}


	public void setId(int id) {
		this.id = id;
	}

	
    public String getOriginalUrl() {
		return originalUrl;
	}


	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}


	public String getShortUrl() {
		return shortUrl;
	}


	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}


	public int getClickCount() {
		return clickCount;
	}


	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	
	
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public int getId() {
		return id;
	}

	
	@Override
	public String toString() {
		return "Url [id=" + id + ", originalUrl=" + originalUrl + ", shortUrl=" + shortUrl + ", createdAt=" + createdAt
				+ ", ClickCount=" + clickCount + "]";
	}



	





    
}
