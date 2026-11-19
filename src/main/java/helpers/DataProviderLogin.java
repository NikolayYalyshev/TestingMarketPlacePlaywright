package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProviderLogin {

    public static Stream<Arguments> providerKeywordsAboutAuthorization(){
        return Stream.of(Arguments.of( "nikolay.yalyshev1@gmail.com", "Odepoz88"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "Odepoz"),
                Arguments.of("nikolay.yalyshev@gmail.com", "плохо")
        );
    }

    public static Stream<Arguments> providerKeywordsAboutAuthorizationFail(){
        return Stream.of(Arguments.of("nikolay.yalyshev1@gmail.com", "111111"),
                Arguments.of("nikolay.yalyshev1@gmail.com", "NikoBelik"),
                Arguments.of("nikolay.yalyshev@gmail.com", "плохо")
        );
    }

    public static Stream<Arguments> providerKeywordsAboutRegistration(){
        return Stream.of(Arguments.of("Николас", "nikolas.cage@gmail.com", "armsdealer"),
                Arguments.of("Николас", "nikolay@gmail.com", "Odep"),
                Arguments.of("ИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИгорь" , "nikolaygmail.com", "плохо")
        );
    }
}
