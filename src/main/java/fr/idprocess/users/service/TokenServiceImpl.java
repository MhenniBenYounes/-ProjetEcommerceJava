package fr.idprocess.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.idprocess.users.model.Token;
import fr.idprocess.users.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public Token findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public void createToken(Token token) {
		Token localToken = tokenRepository.findByToken(token.getToken());

		if (localToken != null) {
			System.out.println("Le jeton :  " + token.getToken() + " est déjà existant!!");
		} else {
			localToken = tokenRepository.save(token);
		}
	}
}
