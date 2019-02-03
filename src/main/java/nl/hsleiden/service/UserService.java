package nl.hsleiden.service;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class UserService extends BaseService<User>
{
    private final UserDAO dao;
    private final PasswordHashingService hasher;
    
    @Inject
    public UserService(UserDAO dao, PasswordHashingService passwordHashingService)
    {
        this.dao = dao;
        this.hasher = passwordHashingService;
    }
    
    public Collection<User> getAll()
    {
        return dao.getAll();
    }
    
    public User get(int id)
    {
        return requireResult(dao.get(id));
    }
    
    public void add(User user)
    {
        user.setRoles(new String[]{"GUEST"});
        user.setPassword(hasher.generateStrongPasswordHash(user.getPassword()));
        dao.add(user);
    }
    
    public void update(User authenticator, int id, User user)
    {
        User oldUser = get(id);
        if (!authenticator.hasRole("ADMIN"))
        {
            assertSelf(authenticator, oldUser);
        }

        user.setPassword(hasher.generateStrongPasswordHash(user.getPassword()));
        dao.update(id, user);
    }
    
    public void delete(User authenticator, int id)
    {
        User user = get(id);
        if (!authenticator.hasRole("ADMIN"))
        {
            assertSelf(authenticator, user);
        }
        
        dao.delete(id);
    }
}
