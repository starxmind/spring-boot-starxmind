package com.starxmind.boot.response;

import com.starxmind.boot.response.exceptions.UnknownResponseCodeException;
import lombok.Getter;

/**
 * 自定义返回
 * 后续拓展支持动态配置
 *
 * @author pizzalord
 * @since 1.0
 */
public enum ResponseCode {
    SUCCESS(0, "操作成功"),
    FAILED(-1, "操作失败"),
    ILLEGAL_REQ(400, "参数错误或请求非法"),
    UNAUTHORIZED(401, "授权无效或过期"),
    FORBIDDEN(403, "资源访问受限"),
    INTERNAL_SERVER_ERROR(500, "服务内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    ;

    @Getter
    private int code;
    @Getter
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过code找打错误码枚举
     *
     * @param code
     * @return
     */
    public static ResponseCode codeOf(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.code == code) {
                return responseCode;
            }
        }
        throw new UnknownResponseCodeException("未捕获到的状态码:" + code);
    }
}
