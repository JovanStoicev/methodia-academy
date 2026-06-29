package filters.registry;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class FilterRegistry {

    private static final Map<String, Filter> FILTERS = new LinkedHashMap<>();

    static {
        register(new GaussianBlurImpl());
        register(new AverageBrightnessBlurImpl());
        register(new BrightnessBlurFilterImpl());
        register(new ColorFilterImpl());
        register(new CropFilterImpl());
    }

    public static void register(Filter filter) {
        FILTERS.put(filter.name(), filter);
    }

    public static Filter get(String name) {
        return FILTERS.get(name);
    }

    public static Collection<Filter> all() {
        return FILTERS.values();
    }
}