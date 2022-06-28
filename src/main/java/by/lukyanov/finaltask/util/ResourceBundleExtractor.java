package by.lukyanov.finaltask.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleExtractor {
    private static final String RESOURCE_BUNDLE_BASE_NAME = "pagecontent";
    private final ResourceBundle resourceBundle;

    static {
        Locale.setDefault(Locale.ROOT);
    }

    public ResourceBundleExtractor(String locale) {
        Locale.forLanguageTag(locale);
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, Locale.forLanguageTag(locale));
    }

    public String getValue(String key){
        return resourceBundle.getString(key);
    }
}
