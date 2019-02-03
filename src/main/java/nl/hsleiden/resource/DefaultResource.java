package nl.hsleiden.resource;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
@Path("/")
public class DefaultResource
{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get()
    {
        return "API working!";
    }
}
