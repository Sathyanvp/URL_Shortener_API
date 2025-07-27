package Url_shortner.url_shortner.DTO;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;



@Schema(description = "Standard API response object containing status, message, and timestamp")
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "404")
    private int statusCode;

    @Schema(description = "Descriptive message of the response", example = "Short URL not found")
    private String message;

    @Schema(description = "Timestamp when the response was generated", example = "2025-07-23T13:00:00")
    private LocalDateTime timestamp;

    public ErrorResponse() {
		
	}


	public ErrorResponse(HttpStatus statusCode, String message, LocalDateTime timestamp) {
    	this.statusCode = statusCode.value();
        this.message = message;
        this.timestamp = timestamp;
        
    }
    
    
    public int getStatusCode() {
  		return statusCode;
  	}

  	

	public void setStatusCode(int statusCode) {
  		this.statusCode = statusCode;
  	}
  	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Error [statusCode=" + statusCode + ", message=" + message + ", timestamp=" + timestamp + "]";
	}


}

 