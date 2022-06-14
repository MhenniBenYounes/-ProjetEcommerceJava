package fr.idprocess.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.idprocess.common.model.Product;
import fr.idprocess.common.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository produitRepos;
	
	@Override
	public List<Product> findAll() {
		List<Product> li = produitRepos.findAll();
		return li;
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> optional = produitRepos.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<Product> findByNom(String nom) {
		return produitRepos.findByNameContainingIgnoreCase(nom);
	}

	@Override
	public void save(Product produit) {
		produitRepos.save(produit);
	}

	@Override
	public void update(Product produit) {
		produitRepos.save(produit);
	}

	@Override
	public void delete(Long id) {
		produitRepos.deleteById(id);

	}

	@Override
	public boolean existsCodeBarreByBarcode(String codebarre) {
		return produitRepos.existsBarcodeByBarcode(codebarre);
	}

}
