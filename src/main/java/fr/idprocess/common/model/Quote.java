package fr.idprocess.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "devis")
public class Quote {

	@Column(name = "devisId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, length = 60)
	@Email(regexp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[A-Za-z]{2,}$")
	@Size(max = 60)
	private String email;

	@Column(name = "nom", nullable = false, length = 30)
	@Size(max = 30)
	private String lastName;

	@Column(name = "prenom", nullable = false, length = 30)
	private String firstName;

	@Column(name = "phone", length = 10)
	@Pattern(regexp = "^\\d{10}$")
	private String phoneNumber;
	
	@Column(name="quantite")
	private short quantity;
	
	@OneToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "produitId")
	private Product produit;
}