package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * Meer informatie over validatie:
 *  http://hibernate.org/validator/
 * 
 * @author Peter van Vliet
 */
public class User implements Principal
{
    @NotNull
    @JsonView(View.Public.class)
    private int id;

    @NotEmpty
    @JsonView(View.Public.class)
    private String username;

    @NotEmpty
    @Length(min = 4)
    @JsonView(View.Internal.class)
    private String password;

    @JsonView(View.Public.class)
    private String[] roles;

    public User(int id, String username, Boolean permission, String password){
        this.id = id;
        this.username = username;
        this.roles = new String[]{permission ? "ADMIN" : "GUEST"};
        this.password = password;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public String getName()
    {
        return username;
    }

    public String[] getRoles()
    {
        return roles;
    }

    public void setRoles(String[] roles)
    {
        this.roles = roles;
    }

    public boolean hasRole(String roleName)
    {
        if (roles != null)
        {
            for(String role : roles)
            {
                if(roleName.equals(role))
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    public boolean equals(User user)
    {
        return username.equals(user.getUsername());
    }
}
