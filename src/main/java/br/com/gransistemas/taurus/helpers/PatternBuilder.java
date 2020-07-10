package br.com.gransistemas.taurus.helpers;

import java.util.regex.Pattern;

public class PatternBuilder {
    private StringBuilder pattern;

    public PatternBuilder() {
        this.pattern = new StringBuilder();
    }

    public PatternBuilder text(String text){
        text = text.replaceAll("([\\\\.\\[{()*+?^$|])", "\\\\$1");

        pattern.append(text);
        return this;
    }

    public PatternBuilder number(String text){
        text = text.replace("d", "\\d");

        pattern.append(text);
        return this;
    }

    public PatternBuilder any(){
        pattern.append(".*");
        return this;
    }

    public Pattern compile(){
        return Pattern.compile(pattern.toString(), Pattern.DOTALL);
    }
}
