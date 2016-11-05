package io.t28.shade.compiler.definitions.preferences;

import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import io.t28.shade.compiler.definitions.MethodDefinition;

public class EditMethodDefinition extends MethodDefinition {
    private static final String NAME = "edit";

    private final ClassName entityClass;
    private final ClassName editorImplClass;

    public EditMethodDefinition(@Nonnull @Named("Entity") ClassName entityClass,
                                @Nonnull @Named("EditorImpl") ClassName editorImplClass) {
        super(Type.NORMAL);
        this.entityClass = entityClass;
        this.editorImplClass = editorImplClass;
    }

    @Nonnull
    @Override
    public Optional<String> name() {
        return Optional.of(NAME);
    }

    @Nonnull
    @Override
    public Optional<ExecutableElement> method() {
        return Optional.empty();
    }

    @Nonnull
    @Override
    public Collection<Class<? extends Annotation>> annotations() {
        return ImmutableList.of(NonNull.class);
    }

    @Nonnull
    @Override
    public Collection<Modifier> modifiers() {
        return ImmutableList.of(Modifier.PUBLIC);
    }

    @Nonnull
    @Override
    public TypeName returnType() {
        return editorImplClass;
    }

    @Nonnull
    @Override
    public Collection<ParameterSpec> parameters() {
        return ImmutableList.of(ParameterSpec.builder(entityClass, "entity")
                .addModifiers(Modifier.FINAL)
                .addAnnotation(NonNull.class)
                .build());
    }

    @Nonnull
    @Override
    public Collection<CodeBlock> statements() {
        return ImmutableList.of(CodeBlock.builder()
                .add("return new $L(this.$N, $N)", editorImplClass, "context", "entity")
                .build());
    }
}