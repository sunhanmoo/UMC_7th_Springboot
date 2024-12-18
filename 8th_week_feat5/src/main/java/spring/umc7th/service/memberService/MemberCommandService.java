package spring.umc7th.service.memberService;

import spring.umc7th.domain.Member;
import spring.umc7th.web.dto.MemberRequestDTO;

public interface MemberCommandService {

    Member joinMember(MemberRequestDTO.JoinDto request);
}
