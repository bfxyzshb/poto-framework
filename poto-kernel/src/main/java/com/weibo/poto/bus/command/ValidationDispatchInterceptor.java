package com.weibo.poto.bus.command;

<<<<<<< HEAD
import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;
import com.weibo.poto.spi.annotation.Activate;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.CollectionUtils;
=======
import com.weibo.poto.exception.PotoException;
import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;
import com.weibo.poto.spi.annotation.Activate;
import com.weibo.poto.spi.annotation.Adaptive;
import org.hibernate.validator.HibernateValidator;
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参数 属性校验
 * Hibernate Validator常用注解
 * aut
 */
<<<<<<< HEAD
@Activate(value = {"validation"})
=======
@Adaptive
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
public class ValidationDispatchInterceptor implements CommandDispatchInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandBus.class);
    /**
     * 使用hibernate的注解来进行验证
     */
    private Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    @Override
<<<<<<< HEAD
    public boolean preIntercept(Command command) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(command);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            constraintViolations.forEach(violation -> {
                logger.error("Field: " + violation.getPropertyPath() + " Message: " + violation.getMessage());
            });
            return false;
        }
        return true;
=======
    public void preIntercept(CommandMessage commandMessage) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(commandMessage);
        constraintViolations.forEach(violation -> {
            logger.debug("Field: " + violation.getPropertyPath() + " Message: " + violation.getMessage());
            throw new PotoException(violation.getPropertyPath() + " " + violation.getMessage());
        });
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    }

}
