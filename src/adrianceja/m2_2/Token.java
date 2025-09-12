/*
 * M2.2
 * Adrian Ceja
 * 1275830
 */

package adrianceja.m2_2;

public class Token {
    public int type;
    public String text;

    public Token(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public String toString() {
        String tname = ListLexer.tokenNames[type];
        return "<'" + text + "'," + tname + ">";
    }
}