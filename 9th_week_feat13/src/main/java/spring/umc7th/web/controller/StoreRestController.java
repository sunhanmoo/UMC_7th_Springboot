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
import spring.umc7th.converter.StoreConverter;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Review;
import spring.umc7th.domain.Store;
import spring.umc7th.domain.mapping.MemberMission;
import spring.umc7th.service.storeService.StoreCommandService;
import spring.umc7th.service.storeService.StoreQueryService;
import spring.umc7th.validation.annotation.CheckPage;
import spring.umc7th.validation.annotation.ExistMember;
import spring.umc7th.validation.annotation.ExistStore;
import spring.umc7th.validation.annotation.MissionInChallenging;
import spring.umc7th.validation.validator.CheckPageValidator;
import spring.umc7th.web.dto.StoreRequestDTO;
import spring.umc7th.web.dto.StoreResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;
    private final CheckPageValidator checkPageValidator;

    @PostMapping("/save")
    public ApiResponse<StoreResponseDTO.CreateStoreResultDTO> createStore(
            @RequestBody @Valid StoreRequestDTO.StoreDTO request) {
        Store store = storeCommandService.createStore(request);
        return ApiResponse.onSuccess(StoreConverter.toCreateStoreResultDTO(store));
    }

    @PostMapping("/{storeId}/save/review")
    public ApiResponse<StoreResponseDTO.CreateReviewResultDTO> createReview(
            @RequestBody @Valid StoreRequestDTO.ReviewDTO request,
            @ExistStore @PathVariable(name = "storeId") Long storeId,
            @ExistMember @RequestParam(name = "memberId") Long memberId) {
        Review review = storeCommandService.createReview(memberId, storeId, request);
        return ApiResponse.onSuccess(StoreConverter.toCreateReviewResultDTO(review));
    }

    @PostMapping("/{storeId}/save/mission")
    public ApiResponse<StoreResponseDTO.CreateMissionResultDTO> createMission(
            @RequestBody @Valid StoreRequestDTO.MissionDTO request,
            @ExistStore @PathVariable(name = "storeId") Long storeId) {
        Mission mission = storeCommandService.createMission(storeId, request);
        return ApiResponse.onSuccess(StoreConverter.toCreateMissionResultDTO(mission));
    }

    @PostMapping("/challenge")
    public ApiResponse<StoreResponseDTO.CreateMemberMissionResultDTO> createChallengingMemberMission(
            @RequestBody @Valid @MissionInChallenging(memberIdField = "memberId", missionIdField = "missionId")
            StoreRequestDTO.ChallengeMissionDTO request) {
        MemberMission memberMission = storeCommandService.createMemberMission(request);
        return ApiResponse.onSuccess(StoreConverter.toCreateMemberMissionDTO(memberMission));
    }

    @GetMapping("{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API", description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE_4001", description = "올바르지 않은 페이징 번호입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_4001", description = "해당하는 가게가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다.")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreViewListDTO> getReviewList(
            @ExistStore @PathVariable(name = "storeId") Long storeId,
            @CheckPage @RequestParam(name = "page") Integer page) {
        Integer validatedPage = checkPageValidator.validateAndTransformPage(page);
        Page<Review> reviewList = storeQueryService.getReviewList(storeId, validatedPage);
        return ApiResponse.onSuccess(StoreConverter.toReviewPreViewListDTO(reviewList));
    }
}
