package bankai.shizuku.ichigo.common.utils;

import bankai.shizuku.ichigo.common.function.FieldFunction;
import bankai.shizuku.ichigo.common.function.SerializableFunction;
import lombok.Data;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * lambda工具类
 */
public class LambdaUtil {

    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<String, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取序列化Lambda, 在此基础上加了缓存。
     *
     * @param function 需要解析的 lambda 对象
     * @param <T>      类型，被调用的 Function 对象的目标类型
     * @return 返回解析后的结果
     */
    public static <T> SerializedLambda getSerializedLambda(SerializableFunction<T, ?> function) {
        if (!function.getClass().isSynthetic()) {
            //合格格式如： "User::getSex"
            throw new RuntimeException("该方法仅能传入 lambda 表达式产生的合成类");
        }
        Class<?> clazz = function.getClass();
        String name = clazz.getName();
        return Optional.ofNullable(FUNC_CACHE.get(name))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda serializedLambda = null;
                    try {
                        Method method = function.getClass().getDeclaredMethod("writeReplace");
                        method.setAccessible(Boolean.TRUE);
                        serializedLambda = (SerializedLambda) method.invoke(function);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                    FUNC_CACHE.put(name, new WeakReference<>(serializedLambda));
                    return serializedLambda;
                });
    }


    /**
     * 获取字段名称
     *
     * @param function lambda 对象，使用格式如: User::getName,返回结果即为name
     * @param <T>      类型，被调用的 Function 对象的目标类型
     * @return 字段名
     */
    public static <T> String getFieldName(FieldFunction<T> function) {
        SerializedLambda serializedLambda = getSerializedLambda(function);
        //得到lambda表达式中调用的方法名，如 "User::getName"，则得到的是"getName"
        String getterMethod = serializedLambda.getImplMethodName();
        //去掉”get"前缀，最终得到字段名“name"
        return Introspector.decapitalize(getterMethod.replace("get", ""));
    }
}

