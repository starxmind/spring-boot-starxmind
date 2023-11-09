package com.starxmind.boot.response;

import com.starxmind.boot.response.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Response advice
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class ResponseControllerAdvice {
    /**
     * 参数校验异常 code:400 (这里专门处理参数问题)
     *
     * @param ex 异常
     * @return 返回
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleParamException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String friendlyMessage = String.format("参数{%s}%s", fieldError.getField(), fieldError.getDefaultMessage());
        log.error(ResponseCode.ILLEGAL_REQ + ": " + fieldError);
        return Response.failWith(ResponseCode.ILLEGAL_REQ.getCode(), friendlyMessage);
    }

    /**
     * 其他参数校验异常: 400 (处理其它因为用户输入或操作导致的异常)
     *
     * @param ex 异常
     * @return 返回
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalRequestException.class})
    public Response handleHttpMessageNotReadableException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Response.failWith(ResponseCode.ILLEGAL_REQ);
    }

    /**
     * 捕获未处理的异常 code:401
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Response handleUnauthorizedException(UnauthorizedException ex) {
        log.error(ResponseCode.UNAUTHORIZED + ": " + ex.getMessage());
        return Response.failWith(ResponseCode.UNAUTHORIZED);
    }

    /**
     * 捕获未处理的异常 code:403
     *
     * @param ex 异常
     * @return 返回
     */
    @ExceptionHandler(ForbiddenException.class)
    public Response handleForbiddenException(ForbiddenException ex) {
        log.error(ResponseCode.FORBIDDEN + ": " + ex.getMessage());
        return Response.failWith(ResponseCode.FORBIDDEN);
    }

    /**
     * 内部服务错误: 500
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(InternalServerException.class)
    public Response handleInternalServerException(InternalServerException ex) {
        log.error(ResponseCode.INTERNAL_SERVER_ERROR + ": " + ex.getMessage(), ex);
        return Response.failWith(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 服务不可用: 503
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    public Response handleInternalServerException(ServiceUnavailableException ex) {
        log.error(ResponseCode.SERVICE_UNAVAILABLE + ": " + ex.getMessage(), ex);
        return Response.failWith(ResponseCode.SERVICE_UNAVAILABLE);
    }

    /**
     * 告警 code:-2
     *
     * @param ex 异常
     * @return 返回
     */
    @ExceptionHandler(WarningException.class)
    public Response handleWarningException(Exception ex) {
        log.error(ResponseCode.WARNING + ": " + ex.getMessage());
        return Response.failWith(ResponseCode.WARNING.getCode(), ex.getMessage());
    }

    /**
     * 捕获未处理的异常 code:-1
     *
     * @param ex 异常
     * @return 返回
     */
    @ExceptionHandler(Exception.class)
    public Response handleCustomerException(Exception ex) {
        log.error(ResponseCode.FAILED + ": " + ex.getMessage(), ex);
        String error = ex.getMessage();
        return Response.fail(StringUtils.isNotBlank(error) ? error : ResponseCode.FAILED.getMessage());
    }
}
