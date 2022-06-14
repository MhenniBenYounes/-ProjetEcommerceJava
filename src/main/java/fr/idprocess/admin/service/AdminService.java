package fr.idprocess.admin.service;

import fr.idprocess.admin.model.Admin;

public interface AdminService {

	Admin findByUsername(String username);

	void saveAdmin(Admin admin);

}
