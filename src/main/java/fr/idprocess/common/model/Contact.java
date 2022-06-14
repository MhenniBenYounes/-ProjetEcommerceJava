package fr.idprocess.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import fr.idprocess.common.validator.XssJsoupSafe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contactId")
	private long id;

	@Column(nullable = false, length = 60)
	@Email(regexp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[A-Za-z]{2,}$")
	@Size(max = 60)
	private String email;

	@Column(name = "nom", nullable = false, length = 30)
	@XssJsoupSafe
	@NotEmpty
	@Size(max = 30)
	private String lastName;

	@Column(name = "prenom", nullable = false, length = 30)
	@XssJsoupSafe
	@NotEmpty
	@Size(max = 30)
	private String firstName;

	@Column(name = "phone",length = 10)
	@Pattern(regexp = "^\\d{10}$")
	private String phoneNumber;
	
	@Column(name="sujet", nullable = false)
	@XssJsoupSafe
	@NotEmpty
	@Size(max = 30)
	private String subject;
	
	@Column(name="message", nullable = false)
	@XssJsoupSafe
	@NotEmpty
	@Size(max = 255)
	private String message;
}
