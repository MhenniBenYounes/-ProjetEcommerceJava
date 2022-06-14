package fr.idprocess;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.idprocess.users.model.User;
import fr.idprocess.users.repository.UserRepository;
import fr.idprocess.users.service.UserService;

@SpringBootTest
class UserServiceTest {

	@Autowired
	UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	@DisplayName("Test findAll Success")
	void testFindAll() {
		
		// Instantiation des objets
		User user1 = new User();
		User user2 = new User();
		
		user1.setEmail("mhenni.by@sfr.fr");
		user1.setFirstName("mhenni");
		user1.setLastName("by");
		user1.setPassword("123123123");
		
		user2.setEmail("mhenni.by@free.fr");
		user2.setFirstName("mhenni");
		user2.setLastName("ben younes");
		user2.setPassword("123123123");

		doReturn(Arrays.asList(user1, user2)).when(userRepository).findAll();

        // Exécution du Service User
        List<User> users = userService.findAll();
        // Réponse attendue pour la liste des users
        Assertions.assertEquals(2, users.size(), "findAll doit retourner 2 clients");
	}
}
