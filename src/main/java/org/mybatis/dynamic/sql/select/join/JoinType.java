
package org.mybatis.dynamic.sql.select.join;

import java.util.Optional;

public enum JoinType {
    INNER(),
    LEFT("left"), //$NON-NLS-1$
    RIGHT("right"), //$NON-NLS-1$
    FULL("full"); //$NON-NLS-1$
    
    private String shortType;
    
    JoinType() {
    }
    
    JoinType(String shortType) {
        this.shortType = shortType;
    }
    
    public Optional<String> shortType() {
        return Optional.ofNullable(shortType);
    }
}
