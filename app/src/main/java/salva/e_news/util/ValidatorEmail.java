package salva.e_news.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorEmail {

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validar email con el formato estandar.
     *
     * @param email para la validacion de email
     * @return verdadero si es veraz, y falso en otro caso
     */
    public static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
