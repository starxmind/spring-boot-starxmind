package com.starxmind.boot.response.options.autoload;

import com.google.common.collect.Maps;
import com.starxmind.boot.response.options.OptionVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 选项数据实例工厂
 *
 * @author pizzalord
 * @since 1.0
 */
public class OptionsFactory implements InitializingBean {
    @Autowired
    private OptionsConfig optionsConfig;

    private final static Map<String, List<OptionVal>> INNER_MAP = Maps.newHashMap();

    /**
     * 初始化加载选项配置
     *
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(optionsConfig.getOptions())) {
            return;
        }
        optionsConfig.getOptions().forEach(x -> INNER_MAP.putIfAbsent(x.getType(), x.getList()));
    }

    /**
     * 根据类型获取所以的选项
     *
     * @param type  类型
     * @param clazz value的class
     * @param <T>   value的类型
     * @return 选项集合
     */
    public static <T> List<OptionVo<T>> getOptions(String type, Class<T> clazz) {
        List<OptionVal> optionVos = INNER_MAP.get(type);
        return optionVos
                .stream().map(
                        x -> new OptionVo<T>().toBuilder()
                                .value(cast(x.getValue(), clazz))
                                .label(x.getLabel())
                                .build()
                ).collect(Collectors.toList());
    }

    private static <T> T cast(String value, Class<T> clazz) {
        if (clazz.equals(String.class)) {
            return (T) value;
        } else if (clazz.equals(Boolean.class)) {
            return (T) Boolean.valueOf(value);
        } else if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(value);
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(value);
        } else if (clazz.equals(Double.class)) {
            return (T) Double.valueOf(value);
        }
        return (T) value;
    }

    /**
     * 获取某个选项具体的值
     *
     * @param type 选项类型
     * @param key  选项key
     * @param <T>  选项类
     * @return 选项label
     */
    public static <T> String getLabel(String type, T key) {
        List<OptionVal> optionVos = INNER_MAP.get(type);
        for (OptionVal optionVo : optionVos) {
            Object value = cast(optionVo.getValue(), key.getClass());
            if (value.equals(key)) {
                return optionVo.getLabel();
            }
        }
        return null;
    }

    /**
     * 获取选项,针对大多数value是字符串的情况
     *
     * @param type 选项类型
     * @return 选项集合
     */
    public static List<OptionVo<String>> getOptions(String type) {
        return getOptions(type, String.class);
    }
}
