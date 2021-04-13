package com.redislabs.edu.redi2read.repositories;

import com.redislabs.edu.redi2read.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

//    The findFirstByEmail method takes advantage of the index we previously created
//    on the email field of the User model.
    User findFirstByEmail(String email);
}