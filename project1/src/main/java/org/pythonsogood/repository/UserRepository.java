package org.pythonsogood.repository;

import org.bson.types.ObjectId;
import org.pythonsogood.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
	@Query("{username:'?0'}")
	User findByUsername(String username);

	@Query("{email:'?0'}")
	User findByEmail(String email);
}
