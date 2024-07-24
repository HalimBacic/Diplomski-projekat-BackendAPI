package backend.multidbapi.multidbapi.models;

import java.util.List;

public class RegisterRequest {
    public String Username;
    public String Password;
    public String Email;
    public List<String> Authorities;
}
