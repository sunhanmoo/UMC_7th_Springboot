package spring.umc7th.service.storeService;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Review;
import spring.umc7th.domain.Store;
import spring.umc7th.domain.mapping.MemberMission;

public interface StoreQueryService {

    Optional<Store> findStore(Long id);

    List<Store> findStoresByNameAnsScore(String name, Float score);

    Optional<Mission> findMission(Long id);

    Optional<MemberMission> findMemberMission(Member member, Mission mission);

    Page<Review> getReviewList(Long storeId, Integer page);
}
