package spring.umc7th.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.umc7th.apiPayload.ApiResponse;
import spring.umc7th.converter.StoreConverter;
import spring.umc7th.domain.Store;
import spring.umc7th.service.storeService.StoreCommandService;
import spring.umc7th.web.dto.StoreRequestDTO;
import spring.umc7th.web.dto.StoreResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    @PostMapping("/save")
    public ApiResponse<StoreResponseDTO.CreateStoreResultDTO> createStore(
            @RequestBody @Valid StoreRequestDTO.StoreDTO request) {
        Store store = storeCommandService.createStore(request);
        return ApiResponse.onSuccess(StoreConverter.toCreateStoreResultDTO(store));
    }
}
