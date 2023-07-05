package org.ksmart.birth.constraints;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HtmlValidator implements ConstraintValidator<Html, String> {

    private Safelist safeList;

    @Override
    public void initialize(final Html annotation) {
        switch (annotation.whiteListType()) {
        case NONE:
            safeList = Safelist.none();
            break;
        case SIMPLE_TEXT:
            safeList = Safelist.simpleText();
            break;
        case BASIC:
            safeList = Safelist.basic();
            break;
        case BASIC_WITH_IMAGE:
            safeList = Safelist.basicWithImages();
            break;
        case RELAXED:
            safeList = Safelist.relaxed();
            break;
        default:
            safeList = Safelist.relaxed();
            break;
        }

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        return Jsoup.isValid(value, safeList);
    }

    public enum List {
        NONE,
        SIMPLE_TEXT,
        BASIC,
        BASIC_WITH_IMAGE,
        RELAXED
    }
}
