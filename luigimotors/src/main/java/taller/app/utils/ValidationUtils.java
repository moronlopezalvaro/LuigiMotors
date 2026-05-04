package taller.app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    // Validar DNI: 8 números y 1 letra
    public static boolean esDniValido(String dni) {
        return dni.matches("\\d{8}[A-HJ-NP-TV-Z]");
    }

    // Validar Teléfono: exactamente 9 números (formato español habitual)
    public static boolean esTelefonoValido(String telefono) {
        return telefono.matches("\\d{9}");
    }

    // Validar Matrícula: 4 números y 3 letras (formato moderno español)
    public static boolean esMatriculaValida(String matricula) {
        // Aceptamos formatos comunes o el moderno 0000XXX
        return matricula.matches("\\d{4}[A-Z]{3}") || matricula.matches("\\d{4} [A-Z]{3}");
    }

    // Validar que la fecha no sea anterior a hoy
    public static boolean esFechaFutura(String fechaStr) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            return !fecha.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
