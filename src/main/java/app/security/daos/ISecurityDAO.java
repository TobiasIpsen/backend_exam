package app.security.daos;

import app.dtos.UserDTO;
import app.security.entities.User;
import app.security.exceptions.ValidationException;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(UserDTO userDTO) throws ValidationException;
    User createUser(UserDTO userDTO);
    User addRole(UserDTO user, String newRole);
}
