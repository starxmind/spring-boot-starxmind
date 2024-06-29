package com.starxmind.boot.response;

import com.starxmind.boot.response.anno.FieldAdvice;
import com.starxmind.boot.response.anno.SkipResponseAdvice;
import com.starxmind.boot.utils.ApplicationContextHolder;
import com.starxmind.boot.utils.ExcludeResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseHandler implements ResponseBodyAdvice<Object>, InitializingBean {
    /**
     * All registered field advices
     */
    private static Collection<FieldAdvice> FIELD_ADVICES;
    private final ApplicationContextHolder applicationContextHolder;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, FieldAdvice> authenticationMap = applicationContextHolder.getApplicationContext().getBeansOfType(FieldAdvice.class);
        FIELD_ADVICES = authenticationMap.values();
        log.info("<Starxmind Authority> has found <{}> authentications", authenticationMap.size());
    }

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
            handleFields(((Response) body).getResult());
            return body;
        }

        // Otherwise, wrap it as a Response
        handleFields(body);
        return Response.success(body);
    }

    private void handleFields(Object result) {
        FIELD_ADVICES.forEach(
                x -> x.handle(result)
        );
    }

}
