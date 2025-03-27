package bankai.shizuku.ichigo.env;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultPropertySource {


    /**
     * 值
     */
    @AliasFor("location") String[] value() default {};

    /**
     * 位置
     */
    @AliasFor("value") String[] location() default {};
}
