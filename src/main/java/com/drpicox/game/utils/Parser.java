package com.drpicox.game.utils;


public class Parser {

    private int index = 0;
    private String text;

    public Parser(String text) {
        if (text == null) this.text = "";
        else this.text = text;
    }

    public boolean hasNext() {
        return index < text.length();
    }

    public char readChar(char ch) {
        if (!hasNext()) throw new Error("Expected '" + ch + "', " + this.toString());
        var n = peek();
        if (n != ch) throw new Error("Expected '" + ch + "' but found '" + n + "', " + this.toString());
        index += 1;
        return ch;
    }

    public int readInt() {
        skipSpaces();

        var sign = 1;
        if (hasNext() && peek() == '-') {
            sign = -1;
            index += 1;
        }

        var mantissa = 0;
        while (hasNext() && Character.isDigit(peek())) {
            mantissa = mantissa * 10 + Character.getNumericValue(peek());
            index += 1;
        }
        return sign * mantissa;
    }

    public String readWord() {
        skipSpaces();

        var sb = new StringBuilder();
        while (hasNext() && Character.isLetter(peek())) {
            sb.append(peek());
            index += 1;
        }
        return sb.toString();
    }

    public String readUntil(char limit) {
        var sb = new StringBuilder();
        while (hasNext() && peek() != limit) {
            sb.append(peek());
            index += 1;
        }
        return sb.toString();
    }

    public String readLine() {
        String r = readUntil('\n');
        if (hasNext()) skipChar('\n');
        return r;
    }

    public Parser skipChar(char ch) {
        readChar(ch);
        return this;
    }

    /*
    public Parser skipLine() {
        readLine();
        return this;
    }
    */

    public Parser skipSpaces() {
        while (hasNext() && Character.isWhitespace(peek())) {
            index += 1;
        }
        return this;
    }

    public char peek() {
        return text.charAt(index);
    }

    @Override
    public String toString() {
        return ">" + text.substring(0, index) + "*" + text.substring(index);
    }
}
