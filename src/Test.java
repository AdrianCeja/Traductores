public class Test {
    public static void main(String[] args) {

    }

}

    public static void main(String[] args) {
        // 1) Si pasas argumentos, los clasifica y termina: java DFALexer if 12e3 +.5
        if (args.length > 0) {
            for (String s : args) {
                System.out.printf("%-16s → %s%n", s, classify(s));
            }
            return;
        }

        // 2) Modo interactivo: escribe líneas con lexemas separados por espacios
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe lexemas separados por espacios. Vacío = ignora. EOF para salir.");
        while (true) {
            System.out.print("> ");
            if (!sc.hasNextLine())
                break; // EOF: Ctrl+D (mac/Linux) o Ctrl+Z+Enter (Windows)
            String line = sc.nextLine().trim();
            if (line.isEmpty())
                continue;

            for (String lex : line.split("\\s+")) {
                System.out.printf("%-16s → %s%n", lex, classify(lex));
            }
        }
        sc.close();
    }
}