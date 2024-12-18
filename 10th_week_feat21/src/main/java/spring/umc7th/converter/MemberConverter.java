package spring.umc7th.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Review;
import spring.umc7th.domain.enums.Gender;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.web.dto.MemberRequestDTO;
import spring.umc7th.web.dto.MemberResponseDTO;
import spring.umc7th.web.dto.MemberResponseDTO.ReviewPreViewDTO;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request){

        Gender gender = switch (request.getGender()) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            case 3 -> Gender.NONE;
            default -> null;
        };

        return Member.builder()
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .gender(gender)
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .memberPreferList(new ArrayList<>())
                .build();
    }

    public static MemberResponseDTO.ReviewPreViewDTO toReviewPreViewDTO(Review review){
        return MemberResponseDTO.ReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getBody())
                .build();
    }

    public static MemberResponseDTO.ReviewPreViewListDTO toReviewPreViewListDTO(Page<Review> reviewList){
        List<ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(MemberConverter::toReviewPreViewDTO).collect(Collectors.toList());

        return MemberResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }

    public static MemberResponseDTO.MissionPreViewDTO toMissionPreViewDTO(MemberMission memberMission) {
        Mission mission = memberMission.getMission();
        return MemberResponseDTO.MissionPreViewDTO.builder()
                .missionSpec(mission.getMissionSpec())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .createdAt(memberMission.getCreatedAt().toLocalDate())
                .build();
    }

    public static MemberResponseDTO.MissionPreViewListDTO toMissionPreViewListDTO(Page<MemberMission> memberMissionList) {
        List<MemberResponseDTO.MissionPreViewDTO> missionPreViewDTOList = memberMissionList.stream()
                .map(MemberConverter::toMissionPreViewDTO).collect(Collectors.toList());

        return MemberResponseDTO.MissionPreViewListDTO.builder()
                .isLast(memberMissionList.isLast())
                .isFirst(memberMissionList.isFirst())
                .totalPage(memberMissionList.getTotalPages())
                .totalElements(memberMissionList.getTotalElements())
                .listSize(missionPreViewDTOList.size())
                .missionList(missionPreViewDTOList)
                .build();
    }

    public static MemberResponseDTO.MemberMissionResultDTO toMemberMissionResultDTO(MemberMission memberMission){
        return MemberResponseDTO.MemberMissionResultDTO.builder()
                .missionId(memberMission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
