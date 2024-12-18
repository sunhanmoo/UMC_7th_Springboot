package spring.umc7th.converter;

import java.util.List;
import java.util.stream.Collectors;
import spring.umc7th.domain.FoodCategory;
import spring.umc7th.domain.mapping.MemberPrefer;

public class MemberPreferConverter {

    public static List<MemberPrefer> toMemberPreferList(List<FoodCategory> foodCategoryList){

        return foodCategoryList.stream()
                .map(foodCategory ->
                        MemberPrefer.builder()
                                .foodCategory(foodCategory)
                                .build()
                ).collect(Collectors.toList());
    }
}
