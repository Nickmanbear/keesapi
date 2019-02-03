package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource
{
    private final UserService service;
    
    @Inject
    public UserResource(UserService service)
    {
        this.service = service;
    }
    
    @GET
    @JsonView(View.Public.class)
    @RolesAllowed("ADMIN")
    public Collection<User> retrieveAll(@Auth User authenticator)
    {
        return service.getAll();
    }
    
    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    @RolesAllowed("ADMIN")
    public Collection<User> retrieve(@Auth User authenticator, @PathParam("id") int id)
    {
        return new ArrayList<User>() {{ add(service.get(id)); }};
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    @RolesAllowed("ADMIN")
    public void create(@Auth User authenticator, @Valid User user)
    {
        service.add(user);
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    @RolesAllowed("ADMIN")
    public void update(@Auth User authenticator, @PathParam("id") int id, @Valid User user)
    {
        service.update(authenticator, id, user);
    }
    
    @DELETE
    @Path("/{id}")
    @JsonView(View.Protected.class)
    @RolesAllowed("ADMIN")
    public void delete(@Auth User authenticator, @PathParam("id") int id)
    {
        service.delete(authenticator, id);
    }
    
    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public Collection<User> authenticate(@Auth User authenticator)
    {
        return new ArrayList<User>() {{ add(authenticator); }};
    }
}
