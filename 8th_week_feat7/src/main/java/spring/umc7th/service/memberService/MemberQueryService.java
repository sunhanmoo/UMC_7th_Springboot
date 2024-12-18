package spring.umc7th.service.memberService;

import java.util.Optional;
import spring.umc7th.domain.Member;

public interface MemberQueryService {

    Optional<Member> findMember(Long id);
}
