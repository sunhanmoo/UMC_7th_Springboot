package spring.umc7th.service.storeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.umc7th.converter.StoreConverter;
import spring.umc7th.domain.Region;
import spring.umc7th.domain.Store;
import spring.umc7th.repository.RegionRepository;
import spring.umc7th.repository.storeRepository.StoreRepository;
import spring.umc7th.web.dto.StoreRequestDTO;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public Store createStore(StoreRequestDTO.StoreDTO request) {
        Region region = regionRepository.findByName(request.getRegion())
                .orElse(Region.builder().name(request.getRegion()).build());
        Store store = StoreConverter.toStore(request, region);

        return storeRepository.save(store);
    }
}
