package com.drpicox.game.tools;

import com.drpicox.game.rooms.Direction;

public class DrawableMap {

    private int width;
    private int height;
    private char[][] map;
    private int dx = 0;
    private int dy = 0;

    public DrawableMap(int width, int height) {
        this.width = width;
        this.height = height;

        map = new char[width][height];
        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                map[x][y] = ' ';
            }
        }
    }

    private DrawableMap(char[][] map, int dx, int dy) {
        this.map = map;
        this.dx = dx;
        this.dy = dy;
    }

    public DrawableMap draw(int x, int y, char ch) {
        map[x + dx][y + dy] = ch;
        return this;
    }

    public DrawableMap drawBox(int sx, int sy, int width, int height) {
        var ex = sx + width - 1;
        var ey = sy + height - 1;

        drawHLine(sx + 1, sy, width - 2);
        drawHLine(sx + 1, ey, width - 2);
        drawVLine(sx, sy + 1, height - 2);
        drawVLine(ex, sy + 1, height - 2);
        draw(sx, sy, '+');
        draw(sx, ey, '+');
        draw(ex, sy, '+');
        draw(ex, ey, '+');

        return this;
    }

    public DrawableMap drawHLine(int sx, int y, int width) {
        return drawHLine(sx, y, width, '-');
    }

    public DrawableMap drawHLine(int sx, int y, int width, char ch) {
        for (var x = sx; x < sx + width; x++) {
            draw(x, y, ch);
        }
        return this;
    }

    public DrawableMap drawVLine(int x, int sy, int height) {
        return drawVLine(x, sy, height, '|');
    }

    public DrawableMap drawVLine(int x, int sy, int height, char ch) {
        for (var y = sy; y < sy + height; y++) {
            draw(x, y, ch);
        }
        return this;
    }

    public DrawableMap drawLine(int x, int y, int length, Direction direction) {
        switch (direction) {
            case NORTH:
                return drawVLine(x, y - length + 1, length);
            case SOUTH:
                return drawVLine(x, y, length);
            case EAST:
                return drawHLine(x, y, length);
            case WEST:
                return drawHLine(x - length + 1, y, length);
        }
        return this;
    }

    public DrawableMap offset(int x, int y) {
        return new DrawableMap(map, dx + x, dy + y);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                result.append(map[x][y]);
            }
            result.append('\n');
        }

        return result.toString();
    }

    public DrawableMap centerText(int x, int y, String text, int width) {
        text = limit(text, width);

        while (text.length() < width) {
            x += 1;
            width -= 2;
        }

        return text(x, y, text);
    }

    public DrawableMap leftText(int x, int y, String text, int width) {
        text = limit(text, width);
        return text(x, y, text);
    }

    public DrawableMap rightText(int x, int y, String text, int width) {
        text = limit(text, width);

        while (text.length() < width) {
            x += 1;
            width -= 1;
        }

        return text(x, y, text);
    }

    private DrawableMap text(int sx, int y, String text) {
        for (var n = 0; n < text.length(); n++) {
            draw(sx + n, y, text.charAt(n));
        }
        return this;
    }

    private String limit(String text, int width) {
        if (text.length() <= width) return text;

        return text.substring(0, width - 3) + "...";
    }
}
