package com.starxmind.boot.response;

import com.starxmind.boot.response.anno.FieldAdvice;
import com.starxmind.boot.response.anno.SkipResponseAdvice;
import com.starxmind.boot.utils.ApplicationContextHolder;
import com.starxmind.boot.utils.ExcludeResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    private final ApplicationContextHolder applicationContextHolder;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // Determine if intercept result by return type and converter type...
        // Do not intercept when the class has annotation of SkipResponseAdvice
        if (returnType.getDeclaringClass().isAnnotationPresent(SkipResponseAdvice.class)) {
            return false;
        }
        // Do not intercept when the method has annotation of SkipResponseAdvice
        Method method = returnType.getMethod();
        if (method == null || method.isAnnotationPresent(SkipResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Exclude exceptional paths
        if (ExcludeResources.match(request.getURI().getPath())) {
            return body;
        }

        // Return itself if it is type already of type Response
        if (body instanceof Response) {
            processResult(((Response) body).getResult());
            return body;
        }

        // Otherwise, wrap it as a Response
        processResult(body);
        return Response.success(body);
    }

    private void processResult(Object result) {
        // Go out if result is null...
        if (result == null) {
            return;
        }

        // Do not process result for primitive type...
        if (ClassUtils.isPrimitiveOrWrapper(result.getClass())) {
            return;
        }

        try {
            processObject(result);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Fatal error at executing field handler!", ex);
        }
    }

    private void processObject(Object obj) throws IllegalAccessException {
        // Go out if result is null...
        if (obj == null) {
            return;
        }

        // Get all fields
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // Make sure get private fields
            field.setAccessible(true);

            // Handle nested object
            if (isNestedDefine(field.getType())) {
                Object nestedObj = field.get(obj);
                if (nestedObj != null) {
                    processResult(nestedObj); // 递归处理对象
                }
            }

            // Handle array
            else if (field.getType().isArray()) {
                Object arrayObj = field.get(obj);
                if (arrayObj != null) {
                    int length = Array.getLength(arrayObj);
                    for (int i = 0; i < length; i++) {
                        Object element = Array.get(arrayObj, i);
                        processResult(element); // 递归处理数组元素
                    }
                }
            }

            // Handl list
            else if (List.class.isAssignableFrom(field.getType())) {
                List<?> listObj = (List<?>) field.get(obj);
                if (listObj != null) {
                    for (Object element : listObj) {
                        processResult(element); // 递归处理列表元素
                    }
                }
            }

            FieldAdvice fieldAdvice = field.getAnnotation(FieldAdvice.class);
            if (fieldAdvice != null) {
                Class<? extends FieldHandler> fieldHandlerClass = fieldAdvice.fieldHandler();
                FieldHandler fieldHandler = applicationContextHolder.getBean(fieldHandlerClass);
                Object value = field.get(obj);
                value = fieldHandler.handle(value);
                field.set(obj, value);
            }
        }
    }

    private boolean isNestedDefine(Class<?> type) {
        return !type.isPrimitive() &&
                !type.getName().startsWith("java.") &&
                !type.isArray() &&
                !List.class.isAssignableFrom(type);
    }

}
