package fr.idprocess.users.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jetons")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jetonId")
	private long id;

	@Column(name = "jeton")
	private String token;

	@Column(name = "dateCreation", columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdDate;

	@ManyToOne
	@JoinColumn(name="client_id", nullable = false)
	private User user;

	public Token(User user) {
		this.user = user;
		this.token = UUID.randomUUID().toString();
		this.createdDate = LocalDate.now();
	}
}
