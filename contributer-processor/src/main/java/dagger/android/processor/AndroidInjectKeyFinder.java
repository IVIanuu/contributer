package dagger.android.processor;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.MoreTypes;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import dagger.MapKey;
import com.ivianuu.contributer.annotations.AndroidInjectorKeyRegistry;

import static com.google.auto.common.AnnotationMirrors.getAnnotationValue;
import static com.google.auto.common.MoreElements.getAnnotationMirror;
import static com.google.auto.common.MoreElements.isAnnotationPresent;
import static com.google.common.collect.Iterables.getOnlyElement;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.lang.model.util.ElementFilter.typesIn;

/**
 * Finds android key registry
 */
final class AndroidInjectKeyFinder implements BasicAnnotationProcessor.ProcessingStep {

    private final Elements elements;
    private final Messager messager;

    private final Map<TypeMirror, TypeMirror> daggerSupportedTypes = new HashMap<>();
    private final Map<TypeMirror, TypeMirror> annotationsAndTypes = new HashMap<>();

    AndroidInjectKeyFinder(Elements elements,
                           Messager messager,
                           Types types) {
        this.elements = elements;
        this.messager = messager;

        // collect all dagger supported types
        Stream.of(
                elements.getPackageElement("dagger.android"),
                elements.getPackageElement("dagger.android.support"))
                .filter(packageElement -> packageElement != null)
                .flatMap(packageElement -> typesIn(packageElement.getEnclosedElements()).stream())
                .filter(type -> isAnnotationPresent(type, MapKey.class))
                .filter(mapKey -> mapKey.getAnnotation(MapKey.class).unwrapValue())
                .flatMap(AndroidInjectKeyFinder::classForAnnotationElement)
                .map(key -> elements.getTypeElement(key.getCanonicalName()).asType())
                .forEach(typeMirror -> daggerSupportedTypes.put(typeMirror, mapKeyValue(typeMirror, elements)));

        annotationsAndTypes.putAll(daggerSupportedTypes);
    }

    ImmutableMap<TypeMirror, TypeMirror> annotationsAndTypes() {
        return ImmutableMap.copyOf(annotationsAndTypes);
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return ImmutableSet.of(AndroidInjectorKeyRegistry.class);
    }

    @Override
    public Set<Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {

        for (Map.Entry<Class<? extends Annotation>, Element> entry : elementsByAnnotation.entries()) {
            AnnotationMirror annotation =
                    getAnnotationMirror(entry.getValue(), AndroidInjectorKeyRegistry.class).get();
            for (TypeMirror mapKey :
                    getAnnotationValue(annotation, "keys").accept(new Util.AllTypesVisitor(), null)) {
                if (daggerSupportedTypes.containsKey(mapKey)) {
                    messager.printMessage(Diagnostic.Kind.ERROR, String.format("%s is automatically supported", mapKey));
                }

                if (isAnnotationPresent(MoreTypes.asElement(mapKey), MapKey.class)) {
                    TypeMirror mapKeyValue = mapKeyValue(mapKey, elements);
                    annotationsAndTypes.put(mapKey, mapKeyValue);
                } else {
                    messager.printMessage(Diagnostic.Kind.ERROR, String.format("%s is not a @MapKey", mapKey), entry.getValue());
                }
            }
        }

        return ImmutableSet.of();
    }

    private static Stream<Class<? extends Annotation>> classForAnnotationElement(TypeElement type) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Annotation> clazz =
                    (Class<? extends Annotation>) Class.forName(type.getQualifiedName().toString());
            return Stream.of(clazz);
        } catch (ClassNotFoundException e) {
            return Stream.of();
        }
    }

    private static TypeMirror mapKeyValue(TypeMirror annotation, Elements elements) {
        List<ExecutableElement> mapKeyMethods =
                methodsIn(elements.getTypeElement(annotation.toString()).getEnclosedElements());
        TypeMirror returnType = getOnlyElement(mapKeyMethods).getReturnType();
        // TODO(ronshapiro): replace with MoreTypes.asWildcard() when auto-common 0.9 is released
        return ((WildcardType) getOnlyElement(MoreTypes.asDeclared(returnType).getTypeArguments()))
                .getExtendsBound();
    }
}
