package QuintoImpacto.testtecnico.utils;

public class MessageRepository {
    public static final String ERROR_NO_ACCESS = "No tienes permiso para realizar esta acción.";
    public static final String ERROR_NO_AUTH = "No te encuentras logueado. No tienes permiso para realizar esta acción.";
    public static final String ERROR_NO_USER = "Usuario desconocido. No tienes permiso para realizar esta acción.";
    public static final String ERROR_LOGIN = "Error al intentar iniciar sesión.";
    public static final String DATA_NOT_FOUND = "No se encontraron datos.";
    public static final String DATA_SAVED_SUCCESSFULLY = "Los datos se han guardado correctamente.";
    public static final String DATA_LOADED_SUCCESSFULLY = "Los datos se han cargado correctamente.";

    public static String getLoginErrorMessage() {
        return ERROR_LOGIN;
    }
    public static String getErrorNoUser() {
        return ERROR_NO_USER;
    }
    public static String getErrorNotAuth() {
        return ERROR_NO_AUTH;
    }
    public static String getDataNotFoundMessage() {
        return DATA_NOT_FOUND;
    }
    public static String getDataSavedSuccessfullyMessage() {
        return DATA_SAVED_SUCCESSFULLY;
    }
    public static String getNoAccessErrorMessage() {
        return ERROR_NO_ACCESS;
    }
    public static String getDataLoadedSuccessfullyMessage() {
        return DATA_LOADED_SUCCESSFULLY;
    }
}
