package fr.idprocess.users.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.idprocess.common.validator.GenderValue;
import fr.idprocess.common.validator.XssJsoupSafe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "clients")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	private static final String BAD_DATA = "Veuillez saisir des données valides!";
	private static final String LENGTH05 = "Code postal maximum 5 chiffres!";
	private static final String LENGTH10 = "Téléphone français uniquement les 10 chiffres!";
	private static final String LENGTH30 = "Ce champ ne doit pas dépasser 30 caractères!";
	private static final String LENGTH40 = "Votre ville au format maximal de 40 caractères!";
	private static final String LENGTH60 = "Votre E-mail au format maximal de 60 caractères!";
	private static final String LENGTH70 = "Votre adresse au format maximal de 70 caractères!";

	/*
	 * Par défaut Hibernate retourne le camelCase de Java avec underscore exemple :
	 * ( clientId ---> client_id ) comme nom de table. Et par défaut mySql affiche
	 * les colonnes par ordre alphabétique après l'ID.
	 */
	@Column(name = "clientId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	@Email(regexp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[A-Za-z]{2,}$", message = BAD_DATA)
	@Size(max = 60, message = LENGTH60)
	private String email;

	@Column(nullable = false /* (length = 255) est par défaut pour Hibernate */)
	@NotEmpty(message = BAD_DATA)
	private String password;

	@Transient
	private String confirmPassword;

	@Column(name = "actif", nullable = false)
	private boolean isEnabled;

	@Column(name = "genre", length = 20)
	@GenderValue
	private String gender;

	@Column(name = "nom", nullable = false, length = 30)
	@XssJsoupSafe(message = BAD_DATA)
	@NotEmpty(message = BAD_DATA)
	@Size(max = 30, message = LENGTH30)
	private String lastName;

	@Column(name = "prenom", nullable = false, length = 30)
	@XssJsoupSafe(message = BAD_DATA)
	@NotEmpty(message = BAD_DATA)
	@Size(max = 30, message = LENGTH30)
	private String firstName;

	@Column(name = "phone", length = 10)
	@Pattern(regexp = "^\\d{10}$", message = LENGTH10)
	private String phoneNumber;

	@Column(name = "adresse", length = 70)
	@XssJsoupSafe(message = BAD_DATA)
	@Size(max = 70, message = LENGTH70)
	private String adress;

	@Column(name = "ville", length = 40)
	@XssJsoupSafe(message = BAD_DATA)
	@Size(max = 40, message = LENGTH40)
	private String city;

	@Column(name = "codePostal", length = 5)
	@Pattern(regexp = "^\\d{5}$", message = LENGTH05)
	private String zipCode;

	@Column(name = "dateNaissance", columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@Column(name = "dateActivation", columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate activationDate;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Token> token;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * !! Very important UserDetailsServices est une interface principale dans le
	 * cadre de Spring Security, qui est utilisée pour récupérer les informations
	 * d'authentification et d'autorisation de l'utilisateur. Donc malgré que je ne
	 * vais pas utilisé ( username ) comme identifiant je dois redéfinir la méthode
	 * ci-dessous avec l'interface ( UserDetails ).
	 */
	@Override
	public String getUsername() {
		return null;
	}
}
