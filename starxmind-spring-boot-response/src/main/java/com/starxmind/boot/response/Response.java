package com.starxmind.boot.response;

import com.starxmind.boot.response.exceptions.WarningException;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Http response body
 *
 * @param <T> Type of result
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Response<T> {
    @ApiModelProperty(
            value = "返回代码:\n" +
                    " 0: 操作成功\n" +
                    " -1: 操作失败\n" +
                    " 400: 参数错误或请求非法\n" +
                    " 401: 授权无效或过期\n" +
                    " 403: 资源访问受限\n" +
                    " 500: 服务内部错误\n" +
                    " 503: 服务不可用\n" +
                    "",
            required = true)
    @NotNull(message = "不能为空")
    private Integer code;

    @ApiModelProperty(value = "返回信息", required = true)
    @NotBlank(message = "不能为空")
    private String message;

    @ApiModelProperty(value = "返回结果", required = true)
    private T result;

    /**
     * success with default message but no result
     *
     * @return
     */
    public static Response success() {
        return success(null);
    }

    /**
     * success with default message
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T result) {
        return success(result, ResponseCode.SUCCESS.getMessage());
    }

    /**
     * success with customized message
     *
     * @param result
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T result, String message) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(message);
        response.setResult(result);
        return response;
    }

    /**
     * Fail with default message
     *
     * @return
     */
    public static Response fail() {
        return failWith(ResponseCode.FAILED);
    }

    /**
     * Fail with customized message
     *
     * @param message Customized error message
     * @return
     */
    public static Response fail(String message) {
        return Response.builder()
                .code(ResponseCode.FAILED.getCode())
                .message(message)
                .build();
    }

    /**
     * Fail with the special error response
     *
     * @param responseCode ResponseCode
     * @return failure response
     */
    public static Response failWith(ResponseCode responseCode) {
        return failWith(responseCode.getCode(), responseCode.getMessage());
    }

    /**
     * Fail with code and message
     *
     * @param code    failure code
     * @param message failure message
     * @return failure response
     */
    public static Response failWith(int code, String message) {
        return Response.builder()
                .code(code)
                .message(message)
                .build();
    }

    /**
     * 断言自身为正确返回
     */
    public void ok() {
        if (code == -2) {
            throw new WarningException(message);
        }
        Assert.isTrue(code == 0, message);
    }

    /**
     * 获取结果,如果为空返回默认值
     *
     * @param defaultValue 默认值
     * @return 结果
     */
    public T getResultOrDefault(T defaultValue) {
        if (this.result == null) {
            return defaultValue;
        }
        return result;
    }
}
