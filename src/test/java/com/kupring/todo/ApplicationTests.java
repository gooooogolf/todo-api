package com.kupring.todo;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.kupring.todo.controller.advice.GlobalExceptionHandlingControllerAdvice;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	public static ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
		final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod,
					final Exception exception) {
				Method method = new ExceptionHandlerMethodResolver(GlobalExceptionHandlingControllerAdvice.class)
						.resolveMethod(exception);
				if (method != null) {
					return new ServletInvocableHandlerMethod(new GlobalExceptionHandlingControllerAdvice(), method);
				}
				return super.getExceptionHandlerMethod(handlerMethod, exception);
			}
		};
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}

}
