package fr.idprocess.common.service;

import java.util.List;

import fr.idprocess.common.model.Product;

public interface ProductService {
	public List<Product> findAll();
	public Product findById(Long id);
	public List<Product> findByNom(String nom);
	public void save(Product produit);
	public void update(Product produit);
	public void delete(Long id);
	public boolean existsCodeBarreByBarcode(String codebarre);
}
