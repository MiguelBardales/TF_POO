package transporte;

public class PasajeroNoRegistradoException extends Exception {
	@Override
	public String getMessage() {
		return "Pasajero no registrado.";
	}
}

