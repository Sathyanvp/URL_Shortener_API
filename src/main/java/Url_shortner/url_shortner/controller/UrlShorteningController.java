package Url_shortner.url_shortner.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import Url_shortner.url_shortner.DTO.ErrorResponse;
import Url_shortner.url_shortner.DTO.StatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import Url_shortner.url_shortner.DTO.UrlRequest;
import Url_shortner.url_shortner.service.UrlShorteningService;

@Tag(name = "URL Shortener", description = "Endpoints for shortening and redirecting URLs")	
@RestController
public class UrlShorteningController {
    
	private UrlShorteningService service;

	
	
	public UrlShorteningController() {
	
	}

    @Autowired
	public UrlShorteningController(UrlShorteningService service) {
		
		this.service = service;
	}


	@PostMapping("/converturl")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Convert a long URL to a short URL")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Short URL created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
	    @ApiResponse(responseCode = "400", description = "Invalid URL", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
	    		examples = @ExampleObject(
			            value = "{\n" +
			                    "  \"status\": \"400\",\n" +
			                    "  \"message\": \"Invalid url\",\n" +
			                    "  \"timestamp\": \"2025-07-23T18:24:42.880Z\"\n" +
			                    "}"
			        ))),
	    @ApiResponse(responseCode = "410", description = "Custom alias already in use", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
		examples = @ExampleObject(
	            value = "{\n" +
	                    "  \"status\": \"410\",\n" +
	                    "  \"message\": \"Custom alias already in use\",\n" +
	                    "  \"timestamp\": \"2025-07-23T18:24:42.880Z\"\n" +
	                    "}"
	        )))
	})
	public ResponseEntity<?> createShortUrl (@RequestBody UrlRequest urlrequest){
		
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(service.convertUrl(urlrequest));
		
		  
	}
	
	
	
	@GetMapping("/{shortUrl}")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
	    summary = "Redirect to the original URL",
	    description = "Redirects the user to the original long URL associated with the short URL",
	    responses = {
	        @ApiResponse(responseCode = "302", description = "Redirect to original URL"),
	        @ApiResponse(responseCode = "404", description = "Short URL not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	        @ApiResponse(responseCode = "410", description = "This URL has expired.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
	        examples = @ExampleObject(	
		            value = "{\n" +
		                    "  \"status\": \"410\",\n" +
		                    "  \"message\": \"This URL has expired\",\n" +
		                    "  \"timestamp\": \"2025-07-23T18:24:42.880Z\"\n" +
		                    "}"
		        )))
	    }
	)
	public ResponseEntity<?> redirect (@PathVariable String shortUrl){
		
		
			return service.redirectUrl(shortUrl);
		
		
}
	
	
	
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/stat/{shortUrl}")
	@Operation(summary = "Get statistics for a short URL")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Statistics retrived successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatResponse.class))),
	    @ApiResponse(responseCode = "404", description = "Short URL not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
	    examples = @ExampleObject(
	            value = "{\n" +
	                    "  \"status\": \"500\",\n" +
	                    "  \"message\": \"Internal server error\",\n" +
	                    "  \"timestamp\": \"2025-07-23T18:24:42.880Z\"\n" +
	                    "}"
	        )))
	})
	public ResponseEntity<?> showStatistics (@PathVariable String shortUrl){
	        StatResponse response = service.getStatistics(shortUrl);
	        return ResponseEntity.ok(response);
	   
	}
	
}
