package com.starxmind.boot.redis.aspects;

import com.google.common.collect.Maps;
import com.starxmind.bass.sugar.ExpressionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public abstract class AbstractAspect {
    protected String getLockName(ProceedingJoinPoint joinPoint, String lockNameExpression) {
        // 取表达式所有变量名
        Set<String> variableNames = ExpressionUtils.extractVariables(lockNameExpression);
        if (CollectionUtils.isEmpty(variableNames)) {
            return lockNameExpression;
        }

        // 计算所有value
        Map<String, Object> parametersMap = paramsMap(joinPoint);
        Map<String, String> variablesMap = Maps.newHashMap();
        for (String variableName : variableNames) {
            Object variableValue;
            if (variableName.contains(".")) {
                int index = variableName.indexOf(".");
                // 参数名
                String parameterName = variableName.substring(0, index);
                Object parameterValue = parametersMap.get(parameterName);
                // "."后面是表达式
                String parameterExpression = variableName.substring(index + 1).trim();
                variableValue = ExpressionUtils.retrieveValue(parameterValue, parameterExpression);
            } else {
                variableValue = parametersMap.get(variableName);
            }
            variablesMap.put(variableName, Objects.toString(variableValue, StringUtils.EMPTY));
        }

        // 替换表达式所有占位参数
        String result = ExpressionUtils.evaluateExpression(lockNameExpression, variablesMap);
        log.debug("lock name is: {}", result);
        return result;
    }

    private Map<String, Object> paramsMap(ProceedingJoinPoint joinPoint) {
        Map<String, Object> retMap = Maps.newHashMap();
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] parameterNames = methodSignature.getParameterNames();
            Object[] parameterValues = joinPoint.getArgs();
            for (int i = 0; i < parameterNames.length; i++) {
                retMap.put(parameterNames[i], parameterValues[i]);
            }
        }
        return retMap;
    }
}
