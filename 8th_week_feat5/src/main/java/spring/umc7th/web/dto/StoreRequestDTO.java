package spring.umc7th.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class StoreDTO {
        @NotBlank
        String region;
        @NotBlank
        String name;
        @NotBlank
        String address;
    }
}
