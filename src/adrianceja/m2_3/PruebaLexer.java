package adrianceja.m2_3;

import java.io.FileReader;
import java.io.IOException;

public class PruebaLexer {
    public static void main(String[] arg) throws LexicalException {
        String entrada = leerPrograma(
                "D:\\Trabajos\\Traductores\\Traductores Java\\Traductores\\src\\adrianceja\\Codigos\\m2.3_pseudo_1.alg");
        PseudoLexer lexer = new PseudoLexer();
        lexer.analizar(entrada);

        System.out.println("*** Análisis léxico ***\n");
        ;

        for (Token t : lexer.getTokens()) {
            System.out.println(t);
        }
    }

    private static String leerPrograma(String nombre) {
        String entrada = "";

        try {
            FileReader reader = new FileReader(nombre);
            int caracter;

            while ((caracter = reader.read()) != -1)
                entrada += (char) caracter;

            reader.close();
            return entrada;
        } catch (IOException e) {
            return "";
        }
    }
}
