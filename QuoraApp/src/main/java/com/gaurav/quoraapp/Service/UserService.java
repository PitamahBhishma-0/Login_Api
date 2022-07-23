package com.gaurav.quoraapp.Service;

import com.gaurav.quoraapp.Dto.UserDto;
import com.gaurav.quoraapp.model.Roles;
import com.gaurav.quoraapp.model.Users;

import java.util.List;

public interface UserService {
    public Users createUser(Users users);

    public List< UserDto > getUserByDto();

    public List< Users > getAllUser();

    public Users getUserById(long id);

    public Users getUserByEmail(String email);

    public Users updateUser(Users users, long id);

    public void deleteUser(long id);

    public Roles saveRole(Roles roles);

}
