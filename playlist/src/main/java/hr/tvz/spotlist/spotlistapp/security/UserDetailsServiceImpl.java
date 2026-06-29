package hr.tvz.spotlist.spotlistapp.security;

import hr.tvz.spotlist.spotlistapp.model.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Korisnik nije pronađen: " + username));

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(a -> new SimpleGrantedAuthority(a.getName()))
                        .collect(Collectors.toList())
        );
    }
}
