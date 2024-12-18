package spring.umc7th.apiPayload.exception.handler;

import spring.umc7th.apiPayload.code.BaseErrorCode;
import spring.umc7th.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}