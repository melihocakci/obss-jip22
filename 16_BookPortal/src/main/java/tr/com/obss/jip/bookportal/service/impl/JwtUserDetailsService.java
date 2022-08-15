package tr.com.obss.jip.bookportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.MyUserDetails;
import tr.com.obss.jip.bookportal.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userService.getUser(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        List<String> role = List.of(user.getRole().getName().toString());
        return new MyUserDetails(user.getUsername(), user.getPassword(), role);
    }
}
