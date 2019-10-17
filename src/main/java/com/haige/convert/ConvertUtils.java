package com.haige.convert;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Archie
 * @date 2019/10/13 22:06
 */
public class ConvertUtils {
    /**
     *  统一转换器
     * @param origin
     * @param convert
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> convert(List<T> origin, Function<T, R> convert) {
        return origin.stream()
                .map(convert)
                .collect(Collectors.toList());
    }
}
