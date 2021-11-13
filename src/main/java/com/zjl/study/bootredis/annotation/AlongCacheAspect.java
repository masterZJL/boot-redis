package com.zjl.study.bootredis.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class AlongCacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.zjl.study.bootredis.annotation.AlongCache)")
    public void cachePointCut(){}

    @Pointcut("@annotation(com.zjl.study.bootredis.annotation.AlongClearCache)")
    public void setCachePointcut() {}

    @Around(value = "cachePointCut()")
    public Object aroundCache(ProceedingJoinPoint joinPoint) {
        Object o = null;
        try {
            //  获取方法上注解的内容
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
            AlongCache alongCache = method.getAnnotation(AlongCache.class);
            String key = alongCache.key();
            String value = alongCache.value();

            //  解析spel，获取key对应的值
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(key);
            EvaluationContext context = new StandardEvaluationContext();

            Object[] args = joinPoint.getArgs();
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
            String cacheKey = value + "::" + expression.getValue(context).toString();

            //  从redis查询数据
            o = redisTemplate.opsForValue().get(cacheKey);
            if (o != null)
                return o;

            //  执行方法
            o = joinPoint.proceed();

            redisTemplate.opsForValue().set(cacheKey, o);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return o;
    }

    @After(value = "setCachePointcut()")
    public void clearCacheAfterUpdate(JoinPoint joinPoint) {
        try {
            //  获取方法上注解的内容
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
            AlongClearCache alongClearCache = method.getAnnotation(AlongClearCache.class);
            String key = alongClearCache.key();
            String value = alongClearCache.value();

            //  解析spel，获取key对应的值
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(key);
            EvaluationContext context = new StandardEvaluationContext();

            Object[] args = joinPoint.getArgs();
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
            String cacheKey = value + "::" + expression.getValue(context).toString();

            //  清除cacheKey对应的缓存
            redisTemplate.delete(cacheKey);
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

}
