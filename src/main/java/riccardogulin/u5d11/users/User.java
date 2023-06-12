package riccardogulin.u5d11.users;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "password", "creditCard", "isAccountNonLocked", "isEnabled", "isCredentialsNonExpired",
		"authorities" })
public class User implements UserDetails {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String email;
	private String password;

	@Convert(converter = CreditCardConverter.class)
	private String creditCard;

	private boolean isEnabled;
	private boolean isCredentialsNonExpired;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	@Enumerated(EnumType.STRING)
	private Role role;

	public User(String name, String surname, String email, String password, String creditCard) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.creditCard = creditCard;
		this.isEnabled = true;
		this.isAccountNonExpired = true;
		this.isCredentialsNonExpired = true;
		this.isAccountNonLocked = true;
		this.role = Role.USER;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}


}