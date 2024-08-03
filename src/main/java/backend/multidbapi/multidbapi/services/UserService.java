package backend.multidbapi.multidbapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.multidbapi.multidbapi.dbmodels.User;
import backend.multidbapi.multidbapi.interfaces.UserInterface;
import backend.multidbapi.multidbapi.models.RegisterRequest;
import backend.multidbapi.multidbapi.models.exceptions.Messages;
import backend.multidbapi.multidbapi.models.exceptions.ServerException;
import backend.multidbapi.multidbapi.repository.UserRepository;

@Service
public class UserService implements UserInterface, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User RegisterUser(RegisterRequest request) throws ServerException {
        String passHash = passwordEncoder.encode(request.Password);
        User user = new User(request.Username, passHash, request.Email, request.Authorities);
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new ServerException(Messages.failtosave);
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
