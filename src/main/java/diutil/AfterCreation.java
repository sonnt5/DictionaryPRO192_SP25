package diutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Annotation tồn tại ở runtime
@Target(ElementType.METHOD)           // Chỉ áp dụng cho class, interface, enum
public @interface AfterCreation {
}
