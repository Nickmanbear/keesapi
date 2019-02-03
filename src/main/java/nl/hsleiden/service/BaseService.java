package nl.hsleiden.service;

import nl.hsleiden.model.User;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Peter van Vliet
 * @param <T>
 */
class BaseService<T>
{
    T requireResult(T model)
    {
        if (model == null)
        {
            throw new NotFoundException();
        }
        
        return model;
    }
    
    void assertSelf(User user1, User user2)
    {
        if (!user1.equals(user2))
        {
            throw new ForbiddenException();
        }
    }
}
