package spring.umc7th.service.storeService;

import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Review;
import spring.umc7th.domain.Store;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.web.dto.StoreRequestDTO;

public interface StoreCommandService {

    Store createStore(StoreRequestDTO.StoreDTO request);

    Review createReview(Long memberId, Long storeId, StoreRequestDTO.ReviewDTO request);

    Mission createMission(Long storeId, StoreRequestDTO.MissionDTO request);

    MemberMission createMemberMission(StoreRequestDTO.ChallengeMissionDTO request);
}
