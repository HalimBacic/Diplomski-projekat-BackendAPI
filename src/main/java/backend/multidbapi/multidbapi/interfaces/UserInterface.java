package backend.multidbapi.multidbapi.interfaces;

import backend.multidbapi.multidbapi.dbmodels.User;
import backend.multidbapi.multidbapi.models.RegisterRequest;
import backend.multidbapi.multidbapi.models.exceptions.ServerException;

public interface UserInterface {
    public User RegisterUser(RegisterRequest request) throws ServerException;
}
