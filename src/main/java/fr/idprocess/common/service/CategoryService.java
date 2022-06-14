package fr.idprocess.common.service;

import java.util.List;

import fr.idprocess.common.model.Category;

public interface CategoryService {

	public List<Category> findAll();
	public Category findById(int id);
	public Category findByLibelle(String libelle);
	public void save(Category categorie);
	public void update(Category categorie);
	public void delete(int id);
}
