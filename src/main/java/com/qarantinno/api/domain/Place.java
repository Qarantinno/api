package com.qarantinno.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Place {

    private Long id;
    private String name;
    private Type type;
    private Modifier modifier;
    private String address;
    private Coordinate coordinate;

    @Getter
    public enum Type {
        any(false),
        product(true);

        Type(boolean plain) {
            this.plain = plain;
        }

        private final boolean plain;
    }

    @Getter
    public enum Modifier {
        any(null, false),
        minimarket("mini", true),
        supermarket("super", true),
        hypermarket("hyper", true);

        private final String displayName;
        private final boolean plain;

        Modifier(String displayName, boolean plain) {
            this.displayName = displayName;
            this.plain = plain;
        }
    }
}
