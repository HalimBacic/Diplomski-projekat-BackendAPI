package backend.multidbapi.multidbapi.interfaces;

import backend.multidbapi.multidbapi.dto.UserDto;
import backend.multidbapi.multidbapi.models.RegisterRequest;
import backend.multidbapi.multidbapi.models.exceptions.ServerException;

public interface UserInterface {
    public UserDto RegisterUser(RegisterRequest request) throws ServerException;
}
