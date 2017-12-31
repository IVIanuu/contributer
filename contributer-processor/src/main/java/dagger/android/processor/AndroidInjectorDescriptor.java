/*
 * Copyright (C) 2017 The Dagger Authors.
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

package dagger.android.processor;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.Map;
import java.util.Optional;

import javax.annotation.processing.Messager;
import javax.inject.Qualifier;
import javax.inject.Scope;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.processor.Util.AllTypesVisitor;
import dagger.android.processor.Util.ErrorReporter;

import static com.google.auto.common.AnnotationMirrors.getAnnotatedAnnotations;
import static com.google.auto.common.AnnotationMirrors.getAnnotationValue;
import static com.google.auto.common.MoreElements.getAnnotationMirror;
import static com.google.auto.common.MoreElements.isAnnotationPresent;
import static javax.lang.model.element.Modifier.ABSTRACT;

/**
 * A descriptor of a generated {@link Module} and {@link dagger.Subcomponent} to be generated from a
 * {@link ContributesAndroidInjector} method.
 */
@AutoValue
abstract class AndroidInjectorDescriptor {

  abstract ClassName injectedType();

  abstract ClassName frameworkType();

  abstract ImmutableSet<AnnotationSpec> scopes();

  abstract ImmutableSet<ClassName> modules();

  abstract ClassName enclosingModule();

  abstract ClassName mapKeyType();

  abstract String methodName();

  AnnotationSpec mapKeyAnnotation() {
    return AnnotationSpec.builder(mapKeyType())
        .addMember("value", "$T.class", injectedType())
        .build();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder injectedType(ClassName injectedType);

    abstract ImmutableSet.Builder<AnnotationSpec> scopesBuilder();

    abstract ImmutableSet.Builder<ClassName> modulesBuilder();

    abstract Builder frameworkType(ClassName frameworkType);

    abstract Builder enclosingModule(ClassName enclosingModule);

    abstract Builder methodName(String methodName);

    abstract Builder mapKeyType(ClassName mapKeyType);

    abstract AndroidInjectorDescriptor build();
  }

  static final class Validator {
    private final Types types;
    private final Messager messager;
    private final AndroidInjectKeyFinder keyFinder;

    Validator(Types types, Messager messager, AndroidInjectKeyFinder keyFinder) {
      this.types = types;
      this.messager = messager;
      this.keyFinder = keyFinder;
    }

    Optional<AndroidInjectorDescriptor> createIfValid(ExecutableElement method) {
      ErrorReporter reporter = new ErrorReporter(method, messager);

      if (!method.getModifiers().contains(ABSTRACT)) {
        reporter.reportError("@ContributesAndroidInjector methods must be abstract");
      }

      if (!method.getParameters().isEmpty()) {
        reporter.reportError("@ContributesAndroidInjector methods cannot have parameters");
      }

      AndroidInjectorDescriptor.Builder builder = new AutoValue_AndroidInjectorDescriptor.Builder();
      builder.methodName(method.getSimpleName().toString());
      TypeElement enclosingElement = MoreElements.asType(method.getEnclosingElement());
      if (!isAnnotationPresent(enclosingElement, Module.class)) {
        reporter.reportError("@ContributesAndroidInjector methods must be in a @Module");
      }
      builder.enclosingModule(ClassName.get(enclosingElement));

      TypeMirror injectedType = method.getReturnType();

      Optional<Map.Entry<TypeMirror, TypeMirror>> maybeFrameworkType =
          keyFinder.annotationsAndTypes()
              .entrySet()
              .stream()
              .filter(frameworkType -> types.isAssignable(injectedType, frameworkType.getValue()))
              .findFirst();

      if (maybeFrameworkType.isPresent()) {
        builder.frameworkType((ClassName) TypeName.get(maybeFrameworkType.get().getValue()));
        if (MoreTypes.asDeclared(injectedType).getTypeArguments().isEmpty()) {
          builder.injectedType(ClassName.get(MoreTypes.asTypeElement(injectedType)));
          builder.mapKeyType(ClassName.get(MoreTypes.asTypeElement(maybeFrameworkType.get().getKey())));
        } else {
          reporter.reportError(
              "@ContributesAndroidInjector methods cannot return parameterized types");
        }
      } else {
        reporter.reportError(String.format("no key found for %s", injectedType));
      }

      AnnotationMirror annotation =
          getAnnotationMirror(method, ContributesAndroidInjector.class).get();
      for (TypeMirror module :
          getAnnotationValue(annotation, "modules").accept(new AllTypesVisitor(), null)) {
        if (isAnnotationPresent(MoreTypes.asElement(module), Module.class)) {
          builder.modulesBuilder().add((ClassName) TypeName.get(module));
        } else {
          reporter.reportError(String.format("%s is not a @Module", module), annotation);
        }
      }

      for (AnnotationMirror scope : getAnnotatedAnnotations(method, Scope.class)) {
        builder.scopesBuilder().add(AnnotationSpec.get(scope));
      }

      for (AnnotationMirror qualifier : getAnnotatedAnnotations(method, Qualifier.class)) {
        reporter.reportError(
            "@ContributesAndroidInjector methods cannot have qualifiers", qualifier);
      }

      return reporter.hasError() ? Optional.empty() : Optional.of(builder.build());
    }
  }
}
