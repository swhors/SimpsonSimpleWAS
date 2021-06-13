package com.simpson.domain.http.util;

import java.util.List;

public class SimpleHtmlFormat {
    final static public String htmlTagStart = "<html>\n";
    final static public String htmlTagEnd = "</html>\n";
    final static public String headTagStart = "<head>\n";
    final static public String headTagEnd = "</head>\n";
    final static public String titleTagStart = "<title>\n";
    final static public String titleTagEnd = "</title>\n";
    final static public String bodyTagStart = "<body>\n";
    final static public String bodyTagEnd = "</body>\n";
    final static public String h1TagStart = "<h1>";
    final static public String h1TagEnd = "</h1>\n";
    final static public String h2TagStart = "<h2>";
    final static public String h2TagEnd = "</h2>\n";
    final static public String h3TagStart = "<h3>";
    final static public String h3TagEnd = "</h3>\n";
    final static public String pTagStart = "<p>";
    final static public String pTagEnd = "</p>\n";
    final static public String lineTag = "<hr>\n";
    final static public String nextLineTag = "<br/>\n";

    static public String getSimpleHtml(String title, String text) {
        return htmlTagStart +
                headTagStart +
                titleTagStart +
                title +
                titleTagEnd +
                headTagEnd +
                bodyTagStart +
                pTagStart +
                text +
                pTagEnd +
                bodyTagEnd +
                htmlTagEnd;
    }
}
