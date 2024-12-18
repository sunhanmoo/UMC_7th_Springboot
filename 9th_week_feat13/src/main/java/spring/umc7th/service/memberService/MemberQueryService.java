package spring.umc7th.service.memberService;

import java.util.Optional;
import org.springframework.data.domain.Page;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Review;

public interface MemberQueryService {

    Optional<Member> findMember(Long id);

    Page<Review> getReviewListByMemberId(Long memberId, Integer page);
}
