package com.secmngsys.global.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.camel.spi.MaskingFormatter;

public class CamelMaskingFormatterHandler implements MaskingFormatter {

    private static final Set DEFAULT_KEYWORDS = new HashSet(Arrays.asList("passphrase", "password", "secretKey"));
    private Set keywords;
    private boolean maskKeyValue;
    private boolean maskXmlElement;
    private boolean maskJson;
    private String maskString = "xxxxx";
    private Pattern keyValueMaskPattern;
    private Pattern xmlElementMaskPattern;
    private Pattern jsonMaskPattern;

    public CamelMaskingFormatterHandler() {
        this(DEFAULT_KEYWORDS, true, true, true);
    }

    public CamelMaskingFormatterHandler(boolean maskKeyValue, boolean maskXml, boolean maskJson) {
        this(DEFAULT_KEYWORDS, maskKeyValue, maskXml, maskJson);
    }

    public CamelMaskingFormatterHandler(Set keywords, boolean maskKeyValue, boolean maskXmlElement, boolean maskJson) {
        this.keywords = keywords;
        setMaskKeyValue(maskKeyValue);
        setMaskXmlElement(maskXmlElement);
        setMaskJson(maskJson);
    }

    public String format(String source) {
        if (keywords == null || keywords.isEmpty()) {
            return source;
        }

        String answer = source;
        if (maskKeyValue) {
            answer = keyValueMaskPattern.matcher(answer).replaceAll("$1\"" + maskString + "\"");
        }
        if (maskXmlElement) {
            answer = xmlElementMaskPattern.matcher(answer).replaceAll("$1" + maskString + "$3");
        }
        if (maskJson) {
            answer = jsonMaskPattern.matcher(answer).replaceAll("$1\"" + maskString + "\"");
        }
        return answer;
    }

    public boolean isMaskKeyValue() {
        return maskKeyValue;
    }

    public void setMaskKeyValue(boolean maskKeyValue) {
        this.maskKeyValue = maskKeyValue;
        if (maskKeyValue) {
            keyValueMaskPattern = createKeyValueMaskPattern(keywords);
        } else {
            keyValueMaskPattern = null;
        }
    }

    public boolean isMaskXmlElement() {
        return maskXmlElement;
    }

    public void setMaskXmlElement(boolean maskXml) {
        this.maskXmlElement = maskXml;
        if (maskXml) {
            xmlElementMaskPattern = createXmlElementMaskPattern(keywords);
        } else {
            xmlElementMaskPattern = null;
        }
    }

    public boolean isMaskJson() {
        return maskJson;
    }

    public void setMaskJson(boolean maskJson) {
        this.maskJson = maskJson;
        if (maskJson) {
            jsonMaskPattern = createJsonMaskPattern(keywords);
        } else {
            jsonMaskPattern = null;
        }
    }

    public String getMaskString() {
        return maskString;
    }

    public void setMaskString(String maskString) {
        this.maskString = maskString;
    }

    protected Pattern createKeyValueMaskPattern(Set keywords) {
        StringBuilder regex = createOneOfThemRegex(keywords);
        if (regex == null) {
            return null;
        }
        regex.insert(0, "([\\w]*(?:");
        regex.append(")[\\w]*[\\s]*?=[\\s]*?)([\\S&&[^'\",\\}\\]\\)]]+[\\S&&[^,\\}\\]\\)>]]*?|\"[^\"]*?\"|'[^']*?')");
        return Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
    }

    protected Pattern createXmlElementMaskPattern(Set keywords) {
        StringBuilder regex = createOneOfThemRegex(keywords);
        if (regex == null) {
            return null;
        }
        regex.insert(0, "(<([\\w]*(?:");
        regex.append(")[\\w]*)(?:[\\s]+.+)*?>[\\s]*?)(?:[\\S&&[^<]]+(?:\\s+[\\S&&[^<]]+)*?)([\\s]*?)");
        return Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
    }

    protected Pattern createJsonMaskPattern(Set keywords) {
        StringBuilder regex = createOneOfThemRegex(keywords);
        if (regex == null) {
            return null;
        }
        regex.insert(0, "(\"(?:[^\"]|(?:\\\"))*?(?:");
        regex.append(")(?:[^\"]|(?:\\\"))*?\"\\s*?\\:\\s*?)(?:\"(?:[^\"]|(?:\\\"))*?\")");
        return Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
    }

    protected StringBuilder createOneOfThemRegex(Set keywords) {
        StringBuilder regex = new StringBuilder();
        if (keywords == null || keywords.isEmpty()) {
            return null;
        }
        String[] strKeywords = (String[]) keywords.toArray(new String[0]);
        regex.append(Pattern.quote(strKeywords[0]));
        if (strKeywords.length > 1) {
            for (int i = 1; i < strKeywords.length; i++) {
                regex.append('|');
                regex.append(Pattern.quote(strKeywords[i]));
            }
        }
        return regex;
    }
}
