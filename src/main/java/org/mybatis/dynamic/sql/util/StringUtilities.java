
package org.mybatis.dynamic.sql.util;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface StringUtilities {

    static String spaceAfter(Optional<String> in) {
        return in.map(s -> s + " ") //$NON-NLS-1$
                .orElse(""); //$NON-NLS-1$
    }

    static String spaceAfter(String in) {
        return in + " "; //$NON-NLS-1$
    }
    
    static String spaceBefore(Optional<String> in) {
        return in.map(s -> " " + s) //$NON-NLS-1$
                .orElse(""); //$NON-NLS-1$
    }

    static String spaceBefore(String in) {
        return " " + in; //$NON-NLS-1$
    }
    
    static String safelyUpperCase(String s) {
        return s == null ? null : s.toUpperCase();
    }

    static UnaryOperator<Stream<String>> upperCaseAfter(UnaryOperator<Stream<String>> valueModifier) {
        UnaryOperator<Stream<String>> ua = s -> s.map(StringUtilities::safelyUpperCase);
        return t -> ua.apply(valueModifier.apply(t));
    }
}
