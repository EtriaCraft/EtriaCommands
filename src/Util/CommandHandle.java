package Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandle {
    
    String name();
    
    String[] perms() default "";
    
    int minargs() default 0;
}
