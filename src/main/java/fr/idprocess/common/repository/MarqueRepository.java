package fr.idprocess.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.idprocess.common.model.Marque;

public interface MarqueRepository extends JpaRepository<Marque, Integer> {

}
