package spring.umc7th.service.memberService;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.umc7th.apiPayload.code.status.ErrorStatus;
import spring.umc7th.apiPayload.exception.handler.FoodCategoryHandler;
import spring.umc7th.converter.MemberConverter;
import spring.umc7th.converter.MemberPreferConverter;
import spring.umc7th.domain.FoodCategory;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.mapping.MemberPrefer;
import spring.umc7th.repository.FoodCategoryRepository;
import spring.umc7th.repository.MemberRepository;
import spring.umc7th.web.dto.MemberRequestDTO;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request) {

        Member newMember = MemberConverter.toMember(request);
        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> foodCategoryRepository.findById(category).orElseThrow(() -> new FoodCategoryHandler(
                        ErrorStatus.FOOD_CATEGORY_NOT_FOUND))).collect(Collectors.toList());

        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);

        for (MemberPrefer memberPrefer : memberPreferList) {
            memberPrefer.setMember(newMember);
        }

        return memberRepository.save(newMember);
    }
}
