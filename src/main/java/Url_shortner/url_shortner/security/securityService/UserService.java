package Url_shortner.url_shortner.security.securityService;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Url_shortner.url_shortner.exception.UserAlreadyExistsException;
import Url_shortner.url_shortner.security.securityModel.Users;
import Url_shortner.url_shortner.security.securityRepository.SecurityRepository;


@Service
public class UserService {
	

	private SecurityRepository repo;
	
	private AuthenticationManager authmanager;
	
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder;
	
	public UserService(SecurityRepository repo, AuthenticationManager authmanager, JWTService jwtService) {
		this.repo = repo;
		this.authmanager = authmanager;
		this.jwtService = jwtService;
		this.encoder = new BCryptPasswordEncoder(12);
	}
	
	 
    public Users  register (Users user) {
    	if (repo.findByUsername(user.getUsername()).isPresent()) { 
            throw new UserAlreadyExistsException("User with username '" + user.getUsername() + "' already exists.");
        }
    	 user.setPassword(encoder.encode(user.getPassword()));
    	 return repo.save(user);
    
    } 
    
    
//   public boolean isUserExists(Users user) {
//	   Optional<Users> isExists = repo.findByUsername(user.getUsername());
//	   if (isExists == null) {
//		   return false;
//		 }
//	    
//	    return true;
//   }
   

	public String verify(Users user) {
//		Authentication authentication = 
		authmanager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//		if (authentication.isAuthenticated()) {
//			throw new BadCredentialsException("Invalid username or password");
//		}
		
		return jwtService.generateToken( user.getUsername());
	}
}
