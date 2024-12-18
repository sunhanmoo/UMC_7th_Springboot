package spring.umc7th.apiPayload.exception.handler;

import spring.umc7th.apiPayload.code.BaseErrorCode;
import spring.umc7th.apiPayload.exception.GeneralException;

public class FoodCategoryHandler extends GeneralException {

    public FoodCategoryHandler(BaseErrorCode code) {
        super(code);
    }
}
