package ua.org.gostroy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.org.gostroy.model.UserDetailsImpl;
import ua.org.gostroy.model.entity.User;
import ua.org.gostroy.repository.UserRepository;

/**
 * Created by Sergey on 6/1/2016.
 */
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException(username);

        return new UserDetailsImpl(user);

    }
}
