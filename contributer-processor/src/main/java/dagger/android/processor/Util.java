package dagger.android.processor;

import com.google.common.collect.ImmutableSet;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.tools.Diagnostic;

import static java.util.stream.Collectors.toList;

/**
 * Utils
 */
final class Util {

    static final class AllTypesVisitor
            extends SimpleAnnotationValueVisitor8<ImmutableSet<TypeMirror>, Void> {
        @Override
        public ImmutableSet<TypeMirror> visitArray(List<? extends AnnotationValue> values, Void aVoid) {
            return ImmutableSet.copyOf(
                    values.stream().flatMap(v -> v.accept(this, null).stream()).collect(toList()));
        }

        @Override
        public ImmutableSet<TypeMirror> visitType(TypeMirror a, Void aVoid) {
            return ImmutableSet.of(a);
        }

        @Override
        protected ImmutableSet<TypeMirror> defaultAction(Object o, Void aVoid) {
            throw new AssertionError(o);
        }
    }

    static class ErrorReporter {
        private final Element subject;
        private final Messager messager;
        private boolean hasError;

        ErrorReporter(Element subject, Messager messager) {
            this.subject = subject;
            this.messager = messager;
        }

        void reportError(String error) {
            hasError = true;
            messager.printMessage(Diagnostic.Kind.ERROR, error, subject);
        }

        void reportError(String error, AnnotationMirror annotation) {
            hasError = true;
            messager.printMessage(Diagnostic.Kind.ERROR, error, subject, annotation);
        }

        boolean hasError() {
            return hasError;
        }
    }

    private Util() {
    }
}
