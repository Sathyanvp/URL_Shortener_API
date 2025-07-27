package Url_shortner.url_shortner.security.SecurityController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import Url_shortner.url_shortner.security.SecurityDTO.LoginResponse;
import Url_shortner.url_shortner.security.SecurityDTO.RegisterResponse;
import Url_shortner.url_shortner.security.securityModel.Users;
import Url_shortner.url_shortner.security.securityService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;


@Tag(name = "Authentication", description = "User login and registration")
@RestController
public class UserController {

	
	private UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
    
	//register
	
	@PostMapping("/register")
	@Operation(
	    summary = "Register a new user",
	    description = "Registers a user with a username and password",
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "User successfully registered",
	            content = @Content(
	                schema = @Schema(implementation = RegisterResponse.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "409",
	            description = "User already exists or registration failed",
	            content = @Content(schema = @Schema(implementation = RegisterResponse.class),
	            		examples = @ExampleObject(
	            	            value = "{\n" +
	            	                    "  \"status\": \"409\",\n" +
	            	                    "  \"message\": \"User already exists or registration failed\",\n" +
	            	                    "  \"timestamp\": \"2025-07-23T18:24:42.880Z\"\n" +
	            	                    "}")
	        )),
	        @ApiResponse(
		            responseCode = "500",
		            description = "Internal server error",
		            content = @Content(schema = @Schema(implementation = RegisterResponse.class),
		            		examples = @ExampleObject(
		            	            value = "{\n" +
		            	                    "  \"status\": \"500\",\n" +
		            	                    "  \"message\": \"Internal server error\",\n" +
		            	                    "  \"timestamp\": \"2025-07-23T18:24:42.880Z\"\n" +
		            	                    "}")
		        ))
	    }
	)
	public ResponseEntity<RegisterResponse> register(@RequestBody Users user) {
		
    	    	service.register(user);
    		    return ResponseEntity.status(HttpStatus.CREATED)
    		            .body(new RegisterResponse("201 CREATED", "User registered successful"));    	
    		   
    	  
	}

	
	
	//Login 
	
	@PostMapping("/login")
	@Operation(
		    summary = "Authenticate user and return JWT token",
		    description = "login with a username and password and return JWT token",
		    responses = {
		    		@ApiResponse(
		    	    responseCode = "200",
		    	    description = "JWT Token returned on successful login",
		    	    content = @Content(
		    	        mediaType = "application/json",
		    	        schema = @Schema(implementation = LoginResponse.class)
		    	    		 
		    	        )),
		    	        @ApiResponse(
		    	            responseCode = "401",
		    	            description = "Invalid username or password",
		    	            content = @Content(schema = @Schema(implementation = LoginResponse.class),
		    	            		examples = @ExampleObject(
		    	            	            value = "{\n" +
		    	            	                    "  \"status\": \"401\",\n" +
		    	            	                    "  \"message\": \"Invalid username or password\",\n" +
		    	            	                    "  \"token\": \"null\"\n" +
		    	            	                    "}"))),
		    	        @ApiResponse(
		    		            responseCode = "500",
		    		            description = "Internal server error",
		    		            content = @Content(schema = @Schema(implementation = RegisterResponse.class),
		    		            		examples = @ExampleObject(
		    		            	            value = "{\n" +
		    		            	                    "  \"status\": \"500\",\n" +
		    		            	                    "  \"message\": \"Internal server error\",\n" +
		    		            	                    "  \"token\": \"null\"\n" +
		    		            	                    "}")
		    		        ))  
	
	
            }
		    )
	    
	
	public ResponseEntity<LoginResponse> login(@RequestBody Users user) {
		try {
	        String token = service.verify(user);
	        return ResponseEntity.ok(new LoginResponse("200 OK", "Login successful", token));
	    } catch (AuthenticationException  ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new LoginResponse("401 UNAUTHORIZED", "Invalid username or password", null));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new LoginResponse("500 INTERNAL SERVER ERROR", "Unexpected error occurred", null));
	    }
		
	}

}
