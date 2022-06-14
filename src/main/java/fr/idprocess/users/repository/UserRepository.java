package fr.idprocess.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.idprocess.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	boolean existsByEmail(String email);
}
