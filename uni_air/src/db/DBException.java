package db;

public class DBException extends RuntimeException {
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
