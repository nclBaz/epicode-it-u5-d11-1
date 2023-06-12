package riccardogulin.u5d11.users.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationPayload {
	@Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")
	String name;
	String surname;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String email;
	String password;
	String creditCard;
}
