package com.weibo.poto.bus.command;

import com.weibo.poto.exception.PotoException;
import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;
import com.weibo.poto.spi.annotation.Activate;
import com.weibo.poto.spi.annotation.Adaptive;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参数 属性校验
 * Hibernate Validator常用注解
 * aut
 */
@Adaptive
public class ValidationDispatchInterceptor implements CommandDispatchInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandBus.class);
    /**
     * 使用hibernate的注解来进行验证
     */
    private Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    @Override
    public void preIntercept(CommandMessage commandMessage) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(commandMessage);
        constraintViolations.forEach(violation -> {
            logger.debug("Field: " + violation.getPropertyPath() + " Message: " + violation.getMessage());
            throw new PotoException(violation.getPropertyPath() + " " + violation.getMessage());
        });
    }

}
