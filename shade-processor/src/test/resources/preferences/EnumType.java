package io.t28.shade.test.type;

import io.t28.shade.annotation.Preferences;
import io.t28.shade.annotation.Property;

@Preferences(name = "io.t28.shade.test")
public enum EnumType {
    TEST {
        @Override
        public String value() {
            return "";
        }
    };

    @Property(key = "test_value")
    public abstract String value();
}