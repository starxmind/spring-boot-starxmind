package com.starxmind.boot.response.options.autoload;

import lombok.Data;

import java.util.List;

/**
 * 自动配置的选项
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class AutoOption {
    private String type;
    private List<OptionVal> list;
}
