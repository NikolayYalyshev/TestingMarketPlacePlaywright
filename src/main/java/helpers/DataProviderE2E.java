package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProviderE2E {

    public static Stream<Arguments> providerKeywordsE2EDeliveryProduct() {
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88"),
                Arguments.of("nikolas.cage@gmail.com", "armsdealer"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88")
        );
    }

    public static Stream<Arguments> providerKeywordsVariousFilters() {
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Apple", "Смартфоны", "Видеокарты", "AMD", "41450", "50300"),
                Arguments.of("nikolas.cage@gmail.com", "armsdealer", "Смартфоны", "Samsung", "Смартфоны", "Видеокарты", "AMD", "85700", "156500"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Samsung", "Ноутбуки", "Видеокарты", "NVIDIA", "41450", "245000")
        );
    }

    public static Stream<Arguments> providerKeywordsGetProductsAtDiscount() {
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Apple", "SAVE10"),
                Arguments.of("nikolas.cage@gmail.com", "armsdealer", "Смартфоны", "Samsung", "FLAT500"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Samsung", "WELCOME")
        );
    }
}
