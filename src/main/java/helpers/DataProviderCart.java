package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProviderCart {

    public static Stream<Arguments> providerKeywordsAuthorization() {
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88"),
                Arguments.of("nikolas.cage@gmail.com", "armsdealer"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88")
        );
    }

    public static Stream<Arguments> providerKeywordsApplyPromocode() {
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "SAVE10"),
                Arguments.of("nikolas.cage@gmail.com", "armsdealer", "FLAT500"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "WELCOME")
        );
    }

    public static Stream<Arguments> providerKeywordsPromocodeNotApply() {
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "WRONG_PROMO_2026"),
                Arguments.of("nikolas.cage@gmail.com", "armsdealer", "FLAT50"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "GOODBYE")
        );
    }
}
