package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.service.ProductService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

@Singleton
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;

    @Inject
    public ProductResource(ProductService service)
    {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Product> retrieveAll()
    {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    public Collection<Product> retrieve(@PathParam("id") int id)
    {
        return new ArrayList<Product>() {{ add(service.get(id)); }};
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @JsonView(View.Protected.class)
    @RolesAllowed("ADMIN")
    public void create(@Auth User authenticator, @Valid Product product)
    {
        service.add(product);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    @RolesAllowed("ADMIN")
    public void update(@Auth User authenticator, @PathParam("id") int id, @Valid Product product)
    {
        service.update(id, product);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@Auth User authenticator, @PathParam("id") int id)
    {
        service.delete(id);
    }
}
