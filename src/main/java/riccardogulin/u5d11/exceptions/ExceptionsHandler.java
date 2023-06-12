package riccardogulin.u5d11.exceptions;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorsPayloadWithErrorsList> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());

		ErrorsPayloadWithErrorsList payload = new ErrorsPayloadWithErrorsList("Ci sono stati errori nel body",
				new Date(), 400,
				errors);

		return new ResponseEntity<ErrorsPayloadWithErrorsList>(payload, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorsPayload> handleBadRequest(BadRequestException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 400);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorsPayload> handleUnauthorized(UnauthorizedException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 401);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.UNAUTHORIZED);
	}
	

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorsPayload> handleNotFound(NotFoundException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 404);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorsPayload> handleGeneric(Exception e) {

		e.printStackTrace();
		ErrorsPayload payload = new ErrorsPayload("Errore Generico", new Date(), 500);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
