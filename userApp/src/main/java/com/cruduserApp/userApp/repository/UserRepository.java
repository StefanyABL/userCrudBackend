package com.cruduserApp.userApp.repository;

import com.cruduserApp.userApp.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
}
