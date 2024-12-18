package spring.umc7th.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.umc7th.apiPayload.ApiResponse;
import spring.umc7th.converter.MemberConverter;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Review;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.service.memberService.MemberCommandService;
import spring.umc7th.service.memberService.MemberQueryService;
import spring.umc7th.validation.annotation.CheckPage;
import spring.umc7th.validation.annotation.ExistMember;
import spring.umc7th.validation.annotation.ExistMission;
import spring.umc7th.validation.validator.CheckPageValidator;
import spring.umc7th.web.dto.MemberRequestDTO;
import spring.umc7th.web.dto.MemberResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final CheckPageValidator checkPageValidator;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @GetMapping("{memberId}/reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "특정 유저가 작성한 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE_4001", description = "올바르지 않은 페이징 번호입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "해당 사용자가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "유저의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다.")
    })
    public ApiResponse<MemberResponseDTO.ReviewPreViewListDTO> getReviewListByMemberId(
            @ExistMember @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page") Integer page) {
        Integer validatedPage = checkPageValidator.validateAndTransformPage(page);
        Page<Review> reviewList = memberQueryService.getReviewListByMemberId(memberId, validatedPage);
        return ApiResponse.onSuccess(MemberConverter.toReviewPreViewListDTO(reviewList));
    }

    @GetMapping("{memberId}/process/missions")
    @Operation(summary = "내가 진행중인 미션 목록 조회 API", description = "특정 유저가 진행중인 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE_4001", description = "올바르지 않은 페이징 번호입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "해당 사용자가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "유저의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다.")
    })
    public ApiResponse<MemberResponseDTO.MissionPreViewListDTO> getMissionListByMember(
            @ExistMember @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page") Integer page) {
        Integer validatedPage = checkPageValidator.validateAndTransformPage(page);
        Page<MemberMission> missionList = memberQueryService.getMemberMissionListByMemberId(memberId, validatedPage);
        return ApiResponse.onSuccess(MemberConverter.toMissionPreViewListDTO(missionList));
    }

    @PostMapping("{memberId}/complete/mission/{missionId}")
    @Operation(summary = "내가 진행중인 미션 진행 완료로 바꾸기 API", description = "유저가 진행중인 미션을 진행 완료로 바꾸는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION_4001", description = "해당 미션이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "해당 사용자가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER_MISSION_4001", description = "해당 멤버 미션이 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION_4003", description = "해당 미션은 이미 완료되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "유저의 아이디, path variable 입니다."),
            @Parameter(name = "missionId", description = "미션의 아이디, path variable 입니다.")
    })
    public ApiResponse<MemberResponseDTO.MemberMissionResultDTO> completeMemberMission(
            @ExistMember @PathVariable(name = "memberId") Long memberId,
            @ExistMission @PathVariable(name = "missionId") Long missionId) {
        MemberMission memberMission = memberCommandService.completeMission(memberId, missionId);
        return ApiResponse.onSuccess(MemberConverter.toMemberMissionResultDTO(memberMission));
    }
}
