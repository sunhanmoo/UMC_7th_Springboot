package spring.umc7th.apiPayload.exception.handler;

import spring.umc7th.apiPayload.code.BaseErrorCode;
import spring.umc7th.apiPayload.exception.GeneralException;

public class MemberMissionHandler extends GeneralException {

    public MemberMissionHandler(BaseErrorCode code) {
        super(code);
    }
}
