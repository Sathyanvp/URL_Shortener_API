package Url_shortner.url_shortner.security.securityService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import Url_shortner.url_shortner.security.securityModel.UserPrincipal;
import Url_shortner.url_shortner.security.securityModel.Users;
import Url_shortner.url_shortner.security.securityRepository.SecurityRepository;


@Service
public  class CoustemUserDetailsService implements UserDetailsService{
	
	
    private SecurityRepository repo;
	
	
	public CoustemUserDetailsService(SecurityRepository repo) {
		this.repo = repo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsername(username).get();
		if (user == null) {
			
			throw new UsernameNotFoundException("user not found");
		}
		
		return new UserPrincipal(user);
	}

}
