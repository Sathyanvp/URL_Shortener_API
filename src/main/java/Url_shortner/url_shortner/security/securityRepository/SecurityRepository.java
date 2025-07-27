package Url_shortner.url_shortner.security.securityRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Url_shortner.url_shortner.security.securityModel.Users;

@Repository
public interface SecurityRepository extends JpaRepository<Users , Integer>{

	Optional<Users> findByUsername(String username);

}
