package by.lukyanov.finaltask.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleExtractor {
    private static final String RESOURCE_BUNDLE_BASE_NAME = "pagecontent";
    private Locale locale;

    static {
        Locale.setDefault(Locale.ROOT);
    }

    public ResourceBundleExtractor(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    public String getValue(String key){
        ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
        return rb.getString(key);
    }
}
