package fr.idprocess.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import fr.idprocess.common.validator.UniqueCodeBarre;
import fr.idprocess.common.validator.XssJsoupSafe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produits")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String LENGTH13 = "Code à barre à 13 chiffres!";
	private static final String BAD_DATA = "Veuillez saisir des données valides!";
	private static final String LENGTH30 = "Ce champs ne doit pas dépasser 30 caractères!";
	private static final String LENGTH60 = "Ce champs ne doit pas dépasser 60 caractères!";
	private static final String PRIXHT_MIN = "Prix hors taxe doit être supérieur à 1€!";
	
	
	@Column(name = "produitId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "codeBarre", nullable = false, length = 13, unique = true)
	@Pattern(regexp = "^\\d{13}$", message = LENGTH13)
	@UniqueCodeBarre
	private String barcode;

	@Column(name = "nomProduit", nullable = false, length = 30)
	@XssJsoupSafe(message = BAD_DATA)
	@NotEmpty(message = BAD_DATA)
	@Size(max = 30, message = LENGTH30)
	private String name;

	@Column(name = "description", nullable = false, length = 60)
	@XssJsoupSafe(message = BAD_DATA)
	@NotEmpty(message = BAD_DATA)
	@Size(max = 60, message = LENGTH60)
	private String description;

	@Column(name = "qteStock", nullable = false)
	@Min(1)
	@Max(5000)
	private short stock;

	@Column(name = "prixHt", nullable = false)
	@DecimalMin(value = "1.00", message = PRIXHT_MIN)
	private double prixHT;

	@Column(name = "image", nullable = false)
	@Lob
	private byte[] image;

	@Transient
	private String imageBase64;

	@Column(name = "dateCreation", columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime creationDate;

	@Column(name = "dateModification", columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime updatingDate;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categorie_id", nullable = false)
	private Category categorie;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "marque_id", nullable = false)
	private Marque marque;

}
