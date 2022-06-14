package fr.idprocess.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.idprocess.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
