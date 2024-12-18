package spring.umc7th.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import spring.umc7th.apiPayload.code.status.ErrorStatus;
import spring.umc7th.validation.annotation.CheckPage;

@Component
public class CheckPageValidator implements ConstraintValidator<CheckPage, Integer> {

    @Override
    public void initialize(CheckPage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || value < 1){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.INVALID_PAGE_NUMBER.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    public Integer validateAndTransformPage(Integer page) {
        return page - 1;
    }
}
