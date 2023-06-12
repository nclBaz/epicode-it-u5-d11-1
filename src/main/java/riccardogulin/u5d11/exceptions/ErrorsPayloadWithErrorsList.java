package riccardogulin.u5d11.exceptions;

import java.util.Date;
import java.util.List;

import lombok.Getter;

@Getter
public class ErrorsPayloadWithErrorsList extends ErrorsPayload {
	private List<String> errors;

	public ErrorsPayloadWithErrorsList(String message, Date timestamp, int internalCode, List<String> errors) {
		super(message, timestamp, internalCode);
		this.errors = errors;
	}

}
