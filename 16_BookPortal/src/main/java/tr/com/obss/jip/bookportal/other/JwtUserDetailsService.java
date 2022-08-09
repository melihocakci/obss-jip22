package tr.com.obss.jip.bookportal.other;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.service.UserService;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.getUser(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
        }

        List<String> role = List.of(user.getRole().getName().toString());
        return new MyUserDetails(user.getUsername(), user.getPassword(), role);
    }
}