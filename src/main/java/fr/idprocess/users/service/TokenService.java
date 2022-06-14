package fr.idprocess.users.service;

import fr.idprocess.users.model.Token;

public interface TokenService {

	Token findByToken(String token);

	void createToken(Token token);

}
