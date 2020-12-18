package asmr.dev_.dbv2.options;

import java.util.HashMap;
import java.util.Map;

public class ArgSplit {
    private final Map<String, Object> options = new HashMap<>();

    public boolean isOption(String id) {
        return this.options.containsKey(id);
    }

    public Object getOption(String id, Object defaultValue) {
        return this.options.getOrDefault(id, defaultValue);
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public static class Builder {
        public static ArgSplit of(String... args) {
            ArgSplit options = new ArgSplit();
            for (String arg : args) {
                String[] part = arg.split("=");
                if (part.length > 1) {
                    Object value = part[1];
                    try {
                        value = Integer.valueOf(Integer.parseInt((String)value));
                    } catch (Exception exception) {}
                    if ((value instanceof String && value.equals("true")) || value.equals("false"))
                        try {
                            value = Boolean.valueOf(Boolean.parseBoolean((String)value));
                        } catch (Exception exception) {}
                    options.options.put(part[0].replace("=", ""), value);
                }
            }
            return options;
        }
    }
}
