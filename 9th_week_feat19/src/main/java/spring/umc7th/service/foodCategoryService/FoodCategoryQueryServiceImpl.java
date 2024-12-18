package spring.umc7th.service.foodCategoryService;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.umc7th.repository.FoodCategoryRepository;

@Service
@RequiredArgsConstructor
public class FoodCategoryQueryServiceImpl implements FoodCategoryQueryService {

    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    public boolean exitsByIds(List<Long> ids) {
        return ids.stream().allMatch(foodCategoryRepository::existsById);
    }
}
