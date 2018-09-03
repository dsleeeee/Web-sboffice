package kr.co.common.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.util.Collection;

@Component
public class CustomCollectionValidator implements Validator {

    private SpringValidatorAdapter validator;

    public CustomCollectionValidator() {
        this.validator = new SpringValidatorAdapter(
                Validation.buildDefaultValidatorFactory().getValidator()
        );
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void validate(Object target, Errors errors) {

        if(target instanceof Collection) {
            Collection collection = (Collection) target;

            for (Object object : collection) {
                ValidationUtils.invokeValidator(validator, object, errors);
            }
        }
        else {
            ValidationUtils.invokeValidator(validator, target, errors);
        }
    }
    
    @SuppressWarnings("rawtypes")
    public void validate(Object target, Errors errors, Object...validationHints) {
        if(target instanceof Collection) {
            Collection collection = (Collection) target;

            for (Object object : collection) {
                ValidationUtils.invokeValidator(validator, object, errors, validationHints);
            }
        }
        else {
            ValidationUtils.invokeValidator(validator, target, errors, validationHints);
        }
    }

}