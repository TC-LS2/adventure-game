package com.drpicox.game.tools;

import com.samskivert.mustache.Mustache;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MustacheGameStringifier extends GameResultStringifier {

    private String body = "";
    private Map<String, String> templates = new HashMap<>();

    public MustacheGameStringifier setBody(String body) {
        this.body = body;
        return this;
    }

    public MustacheGameStringifier setTemplate(String name, String templateBody) {
        templates.put(name, templateBody);
        return this;
    }

    @Override
    public String toString(Map game) {
        var compiler = Mustache.compiler()
                .escapeHTML(false)
                .withLoader((name) -> new StringReader(templates.getOrDefault(name, "")));

        return wrap(compiler.compile(body).execute(game));
    }

    private String wrap(String text) {
        var result = new StringBuilder();
        var offset = 0;

        while (offset < text.length()) {
            var endIndex = Math.min(offset + 50, text.length());
            var chunk = text.substring(offset, endIndex);
            var skip = 0;

            var lineJumpIndex = chunk.lastIndexOf('\n');
            if (lineJumpIndex >= 0) {
                chunk = chunk.substring(0, lineJumpIndex);
                skip += 1;
            }

            if (chunk.length() == 50) {
                var lastSpaceIndex = chunk.lastIndexOf(' ');
                if (lastSpaceIndex > 30) {
                    chunk = chunk.substring(0, lastSpaceIndex);
                    skip += 1;
                }
            }

            result.append(chunk).append("\n");

            offset += chunk.length() + skip;
        }

        return result.toString().substring(0, result.length() - 1);
    }

}
