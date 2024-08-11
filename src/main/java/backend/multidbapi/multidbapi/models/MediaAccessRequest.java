package backend.multidbapi.multidbapi.models;

import java.util.List;

import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.dbmodels.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaAccessRequest {
    public List<String> users;
    public String username;
    public String mediaId;
    public GrantEnum grant;
}
