/*
 * Copyright (c) 2016 Tatsuya Maki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.t28.shade.processor.util;

import com.google.common.base.MoreObjects;
import com.squareup.javapoet.CodeBlock;

import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class SupportedTypeTest {

    @RunWith(Theories.class)
    public static class buildLoadStatement {
        @DataPoints
        @SuppressWarnings("unused")
        public static Fixture[] FIXTURES = {
                Fixture.builder(SupportedType.BOOLEAN)
                        .variable("preferences")
                        .key("test_key")
                        .defValue(null)
                        .expected("preferences.getBoolean(\"test_key\", false)")
                        .build(),
                Fixture.builder(SupportedType.BOOLEAN)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("false")
                        .expected("preferences.getBoolean(\"test_key\", false)")
                        .build(),
                Fixture.builder(SupportedType.BOOLEAN)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("unknown")
                        .expected("preferences.getBoolean(\"test_key\", false)")
                        .build(),
                Fixture.builder(SupportedType.BOOLEAN)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("true")
                        .expected("preferences.getBoolean(\"test_key\", true)")
                        .build(),
                Fixture.builder(SupportedType.FLOAT)
                        .variable("preferences")
                        .key("test_key")
                        .defValue(null)
                        .expected("preferences.getFloat(\"test_key\", 0.0f)")
                        .build(),
                Fixture.builder(SupportedType.FLOAT)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("1.0")
                        .expected("preferences.getFloat(\"test_key\", 1.0f)")
                        .build(),
                Fixture.builder(SupportedType.FLOAT)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("-1.0")
                        .expected("preferences.getFloat(\"test_key\", -1.0f)")
                        .build(),
                Fixture.builder(SupportedType.INT)
                        .variable("preferences")
                        .key("test_key")
                        .defValue(null)
                        .expected("preferences.getInt(\"test_key\", 0)")
                        .build(),
                Fixture.builder(SupportedType.INT)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("10")
                        .expected("preferences.getInt(\"test_key\", 10)")
                        .build(),
                Fixture.builder(SupportedType.INT)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("-10")
                        .expected("preferences.getInt(\"test_key\", -10)")
                        .build(),
                Fixture.builder(SupportedType.LONG)
                        .variable("preferences")
                        .key("test_key")
                        .defValue(null)
                        .expected("preferences.getLong(\"test_key\", 0L)")
                        .build(),
                Fixture.builder(SupportedType.LONG)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("2147483648")
                        .expected("preferences.getLong(\"test_key\", 2147483648L)")
                        .build(),
                Fixture.builder(SupportedType.LONG)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("-2147483649")
                        .expected("preferences.getLong(\"test_key\", -2147483649L)")
                        .build(),
                Fixture.builder(SupportedType.STRING)
                        .variable("preferences")
                        .key("test_key")
                        .defValue(null)
                        .expected("preferences.getString(\"test_key\", \"\")")
                        .build(),
                Fixture.builder(SupportedType.STRING)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("")
                        .expected("preferences.getString(\"test_key\", \"\")")
                        .build(),
                Fixture.builder(SupportedType.STRING)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("default")
                        .expected("preferences.getString(\"test_key\", \"default\")")
                        .build(),
                Fixture.builder(SupportedType.STRING_SET)
                        .variable("preferences")
                        .key("test_key")
                        .defValue(null)
                        .expected("preferences.getStringSet(\"test_key\", java.util.Collections.<java.lang.String>emptySet())")
                        .build(),
                Fixture.builder(SupportedType.STRING_SET)
                        .variable("preferences")
                        .key("test_key")
                        .defValue("default")
                        .expected("preferences.getStringSet(\"test_key\", java.util.Collections.<java.lang.String>emptySet())")
                        .build(),
        };

        @Theory
        public void shouldReturnStatement(@Nonnull Fixture fixture) throws Exception {
            final CodeBlock actual = fixture.underTest.buildLoadStatement(
                    fixture.variable, fixture.key, fixture.defValue
            );

            // verify
            assertThat(actual.toString())
                    .isEqualTo(fixture.expected);
        }

        public static class Fixture {
            private final SupportedType underTest;
            private final String variable;
            private final String key;
            private final String defValue;
            private final String expected;

            private Fixture(Builder builder) {
                this.underTest = builder.underTest;
                this.variable = builder.variable;
                this.key = builder.key;
                this.defValue = builder.defValue;
                this.expected = builder.expected;
            }

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("underTest", underTest)
                        .add("variable", variable)
                        .add("key", key)
                        .add("defValue", defValue)
                        .add("expected", expected)
                        .toString();
            }

            public static Builder builder(@Nonnull SupportedType underTest) {
                return new Builder(underTest);
            }

            public static class Builder {
                private final SupportedType underTest;
                private String variable;
                private String key;
                private String defValue;
                private String expected;

                private Builder(@Nonnull SupportedType underTest) {
                    this.underTest = underTest;
                }

                @Nonnull
                public Builder variable(@Nonnull String variable) {
                    this.variable = variable;
                    return this;
                }

                @Nonnull
                public Builder key(@Nonnull String key) {
                    this.key = key;
                    return this;
                }

                @Nonnull
                public Builder defValue(@Nullable String defValue) {
                    this.defValue = defValue;
                    return this;
                }

                @Nonnull
                public Builder expected(@Nonnull String expected) {
                    this.expected = expected;
                    return this;
                }

                public Fixture build() {
                    return new Fixture(this);
                }
            }
        }
    }
}