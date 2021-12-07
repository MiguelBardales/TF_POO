package transporte;

public class BusNoDisponibleException extends Exception {
	@Override
	public String getMessage() {
		return "No hay bus disponible.";
	}
}

