package fr.idprocess.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.idprocess.users.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Token findByToken(String token);

}
