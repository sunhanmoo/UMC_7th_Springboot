package spring.umc7th.service.storeService;

import java.util.List;
import java.util.Optional;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Store;
import spring.umc7th.domain.mapping.MemberMission;

public interface StoreQueryService {

    Optional<Store> findStore(Long id);

    List<Store> findStoresByNameAnsScore(String name, Float score);

    Optional<Mission> findMission(Long id);

    Optional<MemberMission> findMemberMission(Member member, Mission mission);
}
