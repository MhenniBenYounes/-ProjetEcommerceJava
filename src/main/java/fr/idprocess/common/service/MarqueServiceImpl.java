package fr.idprocess.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.idprocess.common.model.Marque;
import fr.idprocess.common.repository.MarqueRepository;

@Service
public class MarqueServiceImpl implements MarqueService {
	
	@Autowired
	MarqueRepository marqueRepos;
	
	@Override
	public List<Marque> findAll() {
		List<Marque> li = marqueRepos.findAll();
		return li;
	}

	@Override
	public Marque findById(int id) {
		Optional<Marque> optional = marqueRepos.findById(id);
		Marque cat = optional.isPresent() ? optional.get() : null;
		return cat;
	}

	@Override
	public Marque findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Marque marque) {
		marqueRepos.save(marque);

	}

	@Override
	public void update(Marque marque) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
