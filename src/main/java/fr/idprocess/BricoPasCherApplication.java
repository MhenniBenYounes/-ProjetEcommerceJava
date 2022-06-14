package fr.idprocess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.idprocess.admin.model.Admin;
import fr.idprocess.admin.service.AdminService;
import fr.idprocess.common.model.Category;
import fr.idprocess.common.model.Marque;
import fr.idprocess.common.service.CategoryService;
import fr.idprocess.common.service.MarqueService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class BricoPasCherApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BricoPasCherApplication.class, args);
	}

	@Autowired
	private AdminService adminService;
	@Autowired
	private CategoryService categorieService;
	@Autowired
	private MarqueService marqueService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		String adminName = "dhafer";
		String adminPassword = "123123123";

		if (adminService.findByUsername(adminName) == null) {

			Admin admin = new Admin();
			admin.setUsername(adminName);
			admin.setPassword(passwordEncoder.encode(adminPassword));
			adminService.saveAdmin(admin);
			log.info(adminName + " vient d'être sauvegardé dans la BD!");

		} else {
			log.info(adminName + " est un Admin déjà sauvegardé dans la BD!");
		}

		if (categorieService.findAll().size() == 0 && marqueService.findAll().size() == 0) {
			Category cat1 = new Category();
			cat1.setName("Plomberie");
			categorieService.save(cat1);

			Category cat2 = new Category();
			cat2.setName("Quincaillerie");
			categorieService.save(cat2);

			Category cat3 = new Category();
			cat3.setName("Peinture");
			categorieService.save(cat3);

			Category cat4 = new Category();
			cat4.setName("Droguerie");
			categorieService.save(cat4);

			Category cat5 = new Category();
			cat5.setName("Serrurerie");
			categorieService.save(cat5);

			Marque marque1 = new Marque();
			marque1.setName("Bosh");
			marqueService.save(marque1);

			Marque marque2 = new Marque();
			marque2.setName("Hilti");
			marqueService.save(marque2);

			Marque marque3 = new Marque();
			marque3.setName("Black & Decker");
			marqueService.save(marque3);
		}
	}
}
