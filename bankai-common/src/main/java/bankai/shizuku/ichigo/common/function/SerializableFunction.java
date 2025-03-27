package bankai.shizuku.ichigo.common.function;

import java.io.Serializable;
import java.util.function.Function;


/**
 * 序列化funtion
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
