package spring.umc7th.service.tempService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.umc7th.apiPayload.code.status.ErrorStatus;
import spring.umc7th.apiPayload.exception.handler.TempHandler;

@Service
@RequiredArgsConstructor
public class TempQueryServiceImpl implements TempQueryService {

    @Override
    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
    }
}
