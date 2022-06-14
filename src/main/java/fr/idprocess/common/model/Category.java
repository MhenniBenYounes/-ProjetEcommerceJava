package fr.idprocess.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import fr.idprocess.common.validator.XssJsoupSafe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="categories")
public class Category {

    @Column(name = "categorieId")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

	@Column(name = "nomCategorie", nullable = false, length = 30, unique = true)
	@XssJsoupSafe
	@NotEmpty
	@Size(max = 30)
    private String name;
	
	@OneToMany(mappedBy = "categorie", cascade= CascadeType.ALL)
	private List<Product> produits;

	public Category() {
		this.produits = new ArrayList<>();
	}
	
	public void addProduit(Product produit) {
		if(!produits.contains(produit)) {
			produits.add(produit);
			produit.setCategorie(this);
		}
	}
	public void removeProduit(Product produit) {
		if(produits.contains(produit)) {
			produits.remove(produit);
			produit.setCategorie(null);
		}
	}
}