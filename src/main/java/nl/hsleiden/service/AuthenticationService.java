package nl.hsleiden.service;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class AuthenticationService implements Authenticator<BasicCredentials, User>, Authorizer<User>
{
    private final UserDAO dao;
    private final PasswordHashingService hasher;
    
    @Inject
    public AuthenticationService(UserDAO userDAO, PasswordHashingService passwordHashingService)
    {
        this.dao = userDAO;
        this.hasher = passwordHashingService;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials)
    {
        User user = dao.getByUsername(credentials.getUsername());

        if (user != null && hasher.validatePassword(credentials.getPassword(), user.getPassword())) {
            return Optional.of(dao.get(user.getId()));
        }
        
        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String roleName)
    {
        return user.hasRole(roleName);
    }
}
