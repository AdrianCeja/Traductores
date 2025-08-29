package adrianceja.m2_1;

public class Main {

    enum tipoToken {
        IF, ID, NUM, FLOAT, INVALID
    }

    // Estados del automata finito determinista
    enum Estado {
        INICIO,
        SIGNO, // signo "+-" al inicio
        I,
        IF_, // "if" exacto
        ID,
        INT,
        DOT, // para aceptar "5."
        DOT_INICIO, // para ".5"
        SIGNO_DOT, // para "+.5"
        FRAC,
        EXP, // para "e" o "E"
        EXP_SIGNO, // signo del exponente
        EXP_NUM,
        DEAD // algo invalido
    }

    // Mapeo de los estados aceptados
    static tipoToken tipoAceptado(Estado e) {
        switch (e) {
            case IF_:
                return tipoToken.IF;
            case I: // "i" sola cuenta como ID
            case ID:
                return tipoToken.ID;
            case INT:
                return tipoToken.NUM;
            case FRAC:
            case EXP_NUM:
            case DOT:
                return tipoToken.FLOAT;
            default:
                return tipoToken.INVALID;
        }
    }

    public static tipoToken clasificacion(String lexeme) {
        if (lexeme == null || lexeme.isEmpty())
            return tipoToken.INVALID;

        Estado e = Estado.INICIO;

        for (int i = 0; i < lexeme.length(); i++) {
            char c = lexeme.charAt(i);
            boolean esLetra = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            boolean esDigito = (c >= '0' && c <= '9');

            switch (e) {
                case INICIO:
                    if (c == '+' || c == '-') {
                        e = Estado.SIGNO;
                    } else if (c == '.') {
                        e = Estado.DOT_INICIO;
                    } else if (esLetra) {
                        e = (c == 'i') ? Estado.I : Estado.ID; // puede ser if o id
                    } else if (esDigito) {
                        e = Estado.INT; // entero por ahora
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case SIGNO:
                    if (c == '.') {
                        e = Estado.SIGNO_DOT; // +. o -. pero falta digito
                    } else if (esDigito) {
                        e = Estado.INT; // +123 o -123
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                // Se checa si es un if exacto o un ID con "if" al inicio
                // ;-----------------------;
                case I:
                    if (esLetra || esDigito) {
                        e = (c == 'f') ? Estado.IF_ : Estado.ID;
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case IF_:
                    if (esLetra || esDigito) {
                        e = Estado.ID; // "if" con más caracteres = id
                    } else {
                        e = Estado.DEAD;
                    }
                    break;
                // ;-----------------------;

                case ID:
                    if (esLetra || esDigito) {
                        // Se queda en el estado de id
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case INT:
                    if (esDigito) {
                        // se mantiene en int
                    } else if (c == '.') {
                        e = Estado.DOT; // Acepta 5. como float
                    } else if (c == 'e' || c == 'E') {
                        e = Estado.EXP;
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case DOT: // un . despues de un int
                    if (esDigito) {
                        e = Estado.FRAC; // 5.2
                    } else if (c == 'e' || c == 'E') {
                        e = Estado.EXP;
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case DOT_INICIO: // un . al inicio
                    if (esDigito) {
                        e = Estado.FRAC; // .5
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case SIGNO_DOT:
                    if (esDigito) {
                        e = Estado.FRAC; // +.5 o -.5
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case FRAC:
                    if (esDigito) {
                        // se queda igual
                    } else if (c == 'e' || c == 'E') {
                        e = Estado.EXP;
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case EXP:
                    if (esDigito) {
                        e = Estado.EXP_NUM; // 1e5
                    } else if (c == '+' || c == '-') {
                        e = Estado.EXP_SIGNO; // 1e+
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case EXP_NUM:
                    if (esDigito) {
                        // se queda igual
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case EXP_SIGNO:
                    if (esDigito) {
                        e = Estado.EXP_NUM; // 1e+5 o 1E-5
                    } else {
                        e = Estado.DEAD;
                    }
                    break;

                case DEAD:
                    // se queda muerto
                    break;
            }
            if (e == Estado.DEAD)
                break;
        }
        return tipoAceptado(e);
    }

    public static void main(String[] args) {
        String[] tests = {
                // if o IDs
                "if", "i", "if1", "hola123", "ABC", "a9",
                // NUM / FLOAT con signos y puntos
                "123", "+0", "-007",
                "12.34", "1.0", "3.14159", "5.", "-5.", "+5.",
                ".5", "+.5", "-.5",
                "1e10", "+2.5e10", "4E-3", "-7E+8", "5.e2", "+.5E-3",
                // invalidos
                ".", "+.", "-.", "e10", "12e", "12e+", "12e-",
                "IF", "If", "1.2.3", "1e1e1"
        };
        for (String s : tests) {
            System.out.printf("%-8s -> %s%n", s, clasificacion(s));
        }
    }
}