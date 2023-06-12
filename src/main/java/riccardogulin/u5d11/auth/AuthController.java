package riccardogulin.u5d11.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import riccardogulin.u5d11.auth.payloads.AuthenticationSuccessfullPayload;
import riccardogulin.u5d11.exceptions.UnauthorizedException;
import riccardogulin.u5d11.users.User;
import riccardogulin.u5d11.users.UsersService;
import riccardogulin.u5d11.users.payloads.UserLoginPayload;
import riccardogulin.u5d11.users.payloads.UserRegistrationPayload;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private PasswordEncoder bcrypt;


	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Validated UserRegistrationPayload body) {

		body.setPassword(bcrypt.encode(body.getPassword()));
		User createdUser = usersService.create(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UserLoginPayload body)
			throws NotFoundException {

//		// 1. Verificare che l'email dell'utente sia presente nel db
		User user = usersService.findByEmail(body.getEmail());
//		// 2. In caso affermativo devo verificare che la pw corrisponda a quella trovata
//		// nel db


//		if (!body.getPassword().matches(user.getPassword()))
//			throw new UnauthorizedException("Credenziali non valide");
		String plainPW = body.getPassword(); // "1234"
		String hashedPW = user.getPassword(); // "$2a$10$ML/ZNVOjSJl0bOlrpcxeu.ZUq6SraGO1/oKJG4LQFAf8o5ef3leUS"

		if (!bcrypt.matches(plainPW, hashedPW))
			throw new UnauthorizedException("Credenziali non valide");
//		// 3. Se tutto ok --> genero il token
		String token = JWTTools.createToken(user);
		// 4. Altrimenti --> 401 ("Credenziali non valide")

		return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);
	}

}
