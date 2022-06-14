package fr.idprocess.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.idprocess.common.model.Category;
import fr.idprocess.common.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categorieRepos;
	
	@Override
	public List<Category> findAll() {
		List<Category> li = categorieRepos.findAll();
		return li;
	}

	@Override
	public Category findById(int id) {
		Optional<Category> optional = categorieRepos.findById(id);
		Category cat = optional.isPresent() ? optional.get() : null;
		return cat;
	}

	@Override
	public Category findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Category categorie) {
		categorieRepos.save(categorie);

	}

	@Override
	public void update(Category categorie) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
