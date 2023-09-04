package com.starxmind.boot.starter.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * ID实体
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdEntity<T> {
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "不能为空")
    private T id;
}
