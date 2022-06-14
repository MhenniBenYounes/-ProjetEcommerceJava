package fr.idprocess.common.service;

import java.util.List;

import fr.idprocess.common.model.Marque;

public interface MarqueService {

	public List<Marque> findAll();
	public Marque findById(int id);
	public Marque findByLibelle(String libelle);
	public void save(Marque marque);
	public void update(Marque marque);
	public void delete(int id);
}
