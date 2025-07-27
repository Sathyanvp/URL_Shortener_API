package Url_shortner.url_shortner.DTO;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;



@Schema(description = "Response containing statistics about a shortened URL")
public class StatResponse {
	
	

    @Schema(description = "The original URL", example = "http://google.com/blog/why-you-should-use-a-url-shortener")
    private String originalUrl;
    
	@Schema(description = "Domain of the URL", example = "https://google")
    private String domain;
    
    @Schema(description = "The shortened URL", example = "abc123")
    private String shortUrl;

    @Schema(description = "The creation timestamp of the short URL", example = "2025-07-23T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "The expiry timestamp of the short URL", example = "2025-08-23T12:00:00")
    private LocalDateTime expiryDate;

    @Schema(description = "Total number of times the short URL has been accessed", example = "15")
    private int clickCount;

	//constructor
    
	public StatResponse(String originalUrl, String domain, String shortUrl, LocalDateTime createdAt, LocalDateTime  expiryDate, int clickCount) {
	
		this.originalUrl = originalUrl;
		this.domain = domain;
		this.shortUrl = shortUrl;
		this.createdAt = createdAt;
		this.expiryDate = expiryDate;
		this.clickCount = clickCount;
	}
	
    public LocalDateTime getExpiryDate() {
		return expiryDate;
	}
    
    public String getDomain() {
  		return domain;
  	}

  	public void setDomain(String domain) {
  		this.domain = domain;
  	}


	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
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
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	
	@Override
	public String toString() {
		return "StatResponse [originalUrl=" + originalUrl + ", shortUrl=" + shortUrl + ", createdAt=" + createdAt
				+ ", expiryDate=" + expiryDate + ", clickCount=" + clickCount + "]";
	}
    
}
