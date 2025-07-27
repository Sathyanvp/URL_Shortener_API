package Url_shortner.url_shortner.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class UrlRequest {
	

    @Schema(description = "Original URL to shorten", example = "http://google.com/blog/why-you-should-use-a-url-shortener")
    private String originalUrl;

    @Schema(description = "Custom alias for shortened URL (optional)", example = "myalias123")
    private String customAlais;

    @Schema(description = "Domain (optional)", example = "lazyurl.com")
    private String domain;
	

	public UrlRequest() {
		
	}
	


	public String getDomain() {
		return domain;
	}



	public void setDomain(String domain) {
		this.domain = domain;
	}



	public String getCustomAlais() {
		return customAlais;
	}


	public void setCustomAlais(String customAlais) {
		this.customAlais = customAlais;
	}



	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
		
	}
  

	

}
