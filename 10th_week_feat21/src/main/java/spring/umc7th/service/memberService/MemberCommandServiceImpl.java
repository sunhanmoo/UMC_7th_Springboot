package spring.umc7th.service.memberService;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.umc7th.apiPayload.code.status.ErrorStatus;
import spring.umc7th.apiPayload.exception.handler.FoodCategoryHandler;
import spring.umc7th.apiPayload.exception.handler.MemberMissionHandler;
import spring.umc7th.converter.MemberConverter;
import spring.umc7th.converter.MemberPreferConverter;
import spring.umc7th.domain.FoodCategory;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.enums.MissionStatus;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.domain.mapping.MemberPrefer;
import spring.umc7th.repository.FoodCategoryRepository;
import spring.umc7th.repository.MemberMissionRepository;
import spring.umc7th.repository.MemberRepository;
import spring.umc7th.repository.MissionRepository;
import spring.umc7th.web.dto.MemberRequestDTO;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request) {

        Member newMember = MemberConverter.toMember(request);
        newMember.encodePassword(passwordEncoder.encode(request.getPassword()));

        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> foodCategoryRepository.findById(category).orElseThrow(() -> new FoodCategoryHandler(
                        ErrorStatus.FOOD_CATEGORY_NOT_FOUND))).collect(Collectors.toList());

        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);

        for (MemberPrefer memberPrefer : memberPreferList) {
            memberPrefer.setMember(newMember);
        }

        return memberRepository.save(newMember);
    }

    @Override
    @Transactional
    public MemberMission completeMission(Long memberId, Long missionId) {

        Member member = memberRepository.findById(memberId).get();
        Mission mission = missionRepository.findById(missionId).get();

        MemberMission memberMission = memberMissionRepository.findByMemberAndMission(member, mission)
                .orElseThrow(() -> new MemberMissionHandler(ErrorStatus.MEMBER_MISSION_NOT_FOUND));
        if (memberMission.getStatus().equals(MissionStatus.COMPLETE)) {
            throw new MemberMissionHandler(ErrorStatus.MISSION_ALREADY_COMPLETE);
        }

        memberMission.setStatus(MissionStatus.COMPLETE);

        return memberMission;
    }
}
