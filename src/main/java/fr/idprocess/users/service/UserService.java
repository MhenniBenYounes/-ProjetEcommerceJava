package fr.idprocess.users.service;

import java.util.List;

import fr.idprocess.users.model.User;

public interface UserService {

	User findByEmail(String email);
	
	boolean existsByEmail(String email);

	User getConnectedUser();

	List<User> findAll();

	void createUser(User user);

	void updateUser(User user);

	void deleteUser(long id);
}
