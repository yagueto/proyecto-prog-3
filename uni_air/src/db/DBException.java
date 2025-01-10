package db;

public class DBException extends RuntimeException {
    private static final long serialVersionUID = 3376914735239663220L;

	public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(String.format("Error inesperado en %s, posible error en la base de datos", cause.getStackTrace()[0].getClassName()), cause);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
