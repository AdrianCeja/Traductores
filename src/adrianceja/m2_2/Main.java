/*
 * M2.2
 * Adrian Ceja
 * 1275830
 */

package adrianceja.m2_2;

public class Main {
    public static void main(String[] args) {
        // Igual que tu fragmento:
        ListLexer lexer = new ListLexer("[a, b,[c, d]]");
        Token t = lexer.nextToken();
        while (t.type != Lexer.EOF_TYPE) {
            System.out.println(t);
            t = lexer.nextToken();
        }
        System.out.println(t); // EOF
    }
}