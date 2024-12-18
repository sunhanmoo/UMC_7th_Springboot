package spring.umc7th.service.storeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.umc7th.converter.StoreConverter;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Region;
import spring.umc7th.domain.Review;
import spring.umc7th.domain.Store;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.repository.MemberMissionRepository;
import spring.umc7th.repository.MemberRepository;
import spring.umc7th.repository.MissionRepository;
import spring.umc7th.repository.RegionRepository;
import spring.umc7th.repository.ReviewRepository;
import spring.umc7th.repository.storeRepository.StoreRepository;
import spring.umc7th.web.dto.StoreRequestDTO;
import spring.umc7th.web.dto.StoreRequestDTO.ChallengeMissionDTO;
import spring.umc7th.web.dto.StoreRequestDTO.MissionDTO;
import spring.umc7th.web.dto.StoreRequestDTO.ReviewDTO;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    @Transactional
    public Store createStore(StoreRequestDTO.StoreDTO request) {
        Region region = regionRepository.findByName(request.getRegion())
                .orElse(Region.builder().name(request.getRegion()).build());
        Store store = StoreConverter.toStore(request, region);

        return storeRepository.save(store);
    }

    @Override
    public Review createReview(Long memberId, Long storeId, ReviewDTO request) {
        Review review = StoreConverter.toReview(request);

        review.setMember(memberRepository.findById(memberId).get());
        review.setStore(storeRepository.findById(storeId).get());

        return reviewRepository.save(review);
    }

    @Override
    public Mission createMission(Long storeId, MissionDTO request) {
        Mission mission = StoreConverter.toMission(request);
        mission.setStore(storeRepository.findById(storeId).get());
        return missionRepository.save(mission);
    }

    @Override
    public MemberMission createMemberMission(ChallengeMissionDTO request) {
        MemberMission memberMission = StoreConverter.toMemberMission(request);

        memberMission.setMember(memberRepository.findById(request.getMemberId()).get());
        memberMission.setMission(missionRepository.findById(request.getMissionId()).get());

        return memberMissionRepository.save(memberMission);
    }
}
