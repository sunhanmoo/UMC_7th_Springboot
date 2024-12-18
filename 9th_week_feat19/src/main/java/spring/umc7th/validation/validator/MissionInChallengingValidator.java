package spring.umc7th.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.umc7th.apiPayload.code.status.ErrorStatus;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.enums.MissionStatus;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.service.memberService.MemberQueryService;
import spring.umc7th.service.storeService.StoreQueryService;
import spring.umc7th.validation.annotation.MissionInChallenging;

@Component
@RequiredArgsConstructor
public class MissionInChallengingValidator implements ConstraintValidator<MissionInChallenging, Object> {

    private final StoreQueryService storeQueryService;
    private final MemberQueryService memberQueryService;

    private String memberIdField;
    private String missionIdField;

    @Override
    public void initialize(MissionInChallenging constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.memberIdField = constraintAnnotation.memberIdField();
        this.missionIdField = constraintAnnotation.missionIdField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field memberIdField = value.getClass().getDeclaredField(this.memberIdField);
            Field missionIdField = value.getClass().getDeclaredField(this.missionIdField);

            memberIdField.setAccessible(true);
            missionIdField.setAccessible(true);

            Long memberId = (Long) memberIdField.get(value);
            Long missionId = (Long) missionIdField.get(value);

            Optional<Member> memberOpt = memberQueryService.findMember(memberId);
            if (memberOpt.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_NOT_FOUND.toString())
                        .addConstraintViolation();
                return false;
            }

            Member member = memberOpt.get();
            Optional<Mission> missionOpt = storeQueryService.findMission(missionId);
            if (missionOpt.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.MISSION_NOT_FOUND.toString())
                        .addConstraintViolation();
                return false;
            }

            Mission mission = missionOpt.get();
            Optional<MemberMission> memberMissionOpt = storeQueryService.findMemberMission(member, mission);

            if (memberMissionOpt.isPresent() && isChallengingState(memberMissionOpt.get().getStatus())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.MISSION_ALREADY_CHALLENGING.toString())
                        .addConstraintViolation();
                return false;
            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._BAD_REQUEST.toString())
                    .addConstraintViolation();
            return false;
        }
    }

    private boolean isChallengingState(MissionStatus status) {
        return MissionStatus.CHALLENGING.equals(status);
    }
}
