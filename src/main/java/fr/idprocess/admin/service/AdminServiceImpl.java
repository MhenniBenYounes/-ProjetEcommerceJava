package fr.idprocess.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.idprocess.admin.model.Admin;
import fr.idprocess.admin.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

//	public AdminServiceImpl() {}
//	
//	@Autowired
//	public AdminServiceImpl(AdminRepository adminRepository) {
//		this.adminRepository = adminRepository;
//	}
	/* Au lieu d'écrire ce qui précède avec Spring5, on peut injecter directement sans créer
	 * des constructeurs ou des setters dans le contexte de spring. */
	 
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin findByUsername(String username) {
		return adminRepository.findByUsername(username);
	}

	@Override
	public void saveAdmin(Admin admin) {
		Admin localAdmin = adminRepository.findByUsername(admin.getUsername());

		if (localAdmin != null) {
			System.out.println("Erreur l'administrateur :  " + admin.getUsername() + " est déjà existant!!");

		} else {
			localAdmin = adminRepository.save(admin);
		}
	}
}
