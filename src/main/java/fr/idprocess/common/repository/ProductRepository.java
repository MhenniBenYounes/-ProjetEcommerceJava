package fr.idprocess.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.idprocess.common.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContainingIgnoreCase(String nom);

	boolean existsBarcodeByBarcode(String codebarre);

}
