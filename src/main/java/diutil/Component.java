package diutil;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Component {
    ComponentType type() default ComponentType.CONTROLLER;
    public enum ComponentType {
        VIEW, CONTROLLER, REPOSITORY,SUB,SERVICE
    }
}


