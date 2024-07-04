package com.y.Y.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilityServiceTest {

    private static Stream<Arguments> provideEqualObjectsForConvertObjectToJson(){
        return Stream.of(
                Arguments.of(new Date(123456), new Date(123456)),
                Arguments.of(Set.of(1,2,3,4,5,6), Set.of(1,2,3,4,5,6)),
                Arguments.of(new Exception("An error has occurred"), new Exception("An error has occurred")),
                Arguments.of(LocalDate.of(1999,9,9), LocalDate.of(1999,9,9)),
                Arguments.of(Pattern.compile("x+w*"), Pattern.compile("x+w*")),
                Arguments.of(Integer.toHexString(31414), Integer.toHexString(31414))
        );
    }

    private static Stream<Arguments> provideNonEqualObjectsForConvertObjectToJson(){
        return Stream.of(
                Arguments.of(new Date(123456), new Date(1234)),
                Arguments.of(Set.of(1,2,3,4,5,6), Set.of(1,2,3,4,5,6,10)),
                Arguments.of(new Exception("An error has occurred"), new Exception("An error has not occurred")),
                Arguments.of(LocalDate.of(1999,9,9), LocalDate.of(2019,9,9)),
                Arguments.of(Pattern.compile("x+w*"), Pattern.compile("x+w*abc")),
                Arguments.of(Integer.toHexString(31414), Integer.toHexString(32323))
        );
    }

@ParameterizedTest(name = "with objects - \"{0}\" and \"{1}\"")
@DisplayName("convertObjectToJson Test Cases When Both Objects Are Equal")
@MethodSource("provideEqualObjectsForConvertObjectToJson")
void convertObjectToJson_jsonObjectsShouldBeEqual(Object o1, Object o2) throws JsonProcessingException {
        assertEquals(UtilityService.convertObjectToJson(o1), UtilityService.convertObjectToJson(o2));
}

@ParameterizedTest(name = "with objects - \"{0}\" and \"{1}\"")
@DisplayName("convertObjectToJson Test Cases When Both Objects Are Equal")
@MethodSource("provideNonEqualObjectsForConvertObjectToJson")
void convertObjectToJson_jsonObjectsShouldNotBeEqual(Object o1, Object o2) throws JsonProcessingException {
    assertNotEquals(UtilityService.convertObjectToJson(o1), UtilityService.convertObjectToJson(o2));
}
@DisplayName("hasHashtag Test Cases When No Hashtag is Present")
@CsvSource({"hello!", "Nulla facilisi. Cras quis.", "Mauris mattis mi", "Suspendisse potenti. Vestibulum", "3  ", "#" ,"  #", "cats#", "#   ", "  #  "})
@ParameterizedTest(name = "with string - \"{0}\"")
void hasHashtag_noHastagInStringShouldBeFalse(String str) {
    assertFalse(UtilityService.hasHashtag(str));
}
@DisplayName("hasHastag Test Cases When Null is Passed")
@ParameterizedTest(name = "with string - \"{0}\"")
@NullSource
void hasHashtag_nullValueShouldThrow(String str){
    assertThrows(IllegalArgumentException.class,() -> UtilityService.hasHashtag(str));
}
@DisplayName("hasHastag Test Cases When a Hashtag is Present")
@ParameterizedTest(name = "with string - \"{0}\"")
@CsvSource({"#hello", "fwowi #fweioa fweoaijf", "#pfwe fweifj", "   #fw##o#", "## ### ## ###tefw", "#fwei #powfl", "##fm", "#oj#ijo", "vwpo #ofwi ioj #a[qw f ###k", "  #Pe;l "})
void hasHashtag_hashtagInStringShouldBeTrue(String str){
    assertTrue(UtilityService.hasHashtag(str));
}

@DisplayName("hasHastag Test Cases When Empty String is Passed")
@ParameterizedTest(name = "with string - \"{0}\"")
@EmptySource
void hasHashtag_emptyStringShouldBeFalse(String str){
    assertFalse(UtilityService.hasHashtag(str));
}

@DisplayName("extractHashtagNames Test Cases When No Hashtag is Extracted")
@CsvSource({"hello!", "Nulla facilisi. Cras quis.", "Mauris mattis mi", "Suspendisse potenti. Vestibulum", "3  ", "#" ,"  #", "cats#", "#   ", "  #  "})
@ParameterizedTest(name = "with string - \"{0}\"")
void extractHashtagNames_shouldExtractNoHashtags(String str){
    var extractedHashtags = UtilityService.extractHashtagNames(str);
    assertEquals(0, extractedHashtags.size());
    assertArrayEquals(new String[]{}, extractedHashtags.toArray());
}

@ParameterizedTest(name = "with string - \"{0}\"")
@CsvSource({"gje;ar#of ofeiwaPJ VIB", "N#ulla facilisi. Cras# quis.", "Mauris mattis mi#gg F # f", "Suspendi##sse po2F  F23VeF", "#3  #", "#T" ,"  RR#320FL", "#cats#", "R3W..RFW  LE #gut123FK", " ##########L"})
@DisplayName("extractHashtagNames Test Cases When Exactly One Hashtag is Extracted")
void extractHashtagNames_shouldExtractOneHashtag(String str){
    var extractedHashtags = UtilityService.extractHashtagNames(str);
    assertEquals(1, extractedHashtags.size());
}

    @ParameterizedTest(name = "with string - \"{0}\" and \"{1}\"")
    @DisplayName("extractHashtagNames Test Cases When Exactly One Hashtag is Extracted")
    @CsvSource({"#test1,#test1", "fqwijo#forward #tea,#forward fqfoj.w#tea fm.fe","######tryler ###, #tryler ll. ##", "12#3#4#5678, #3 #5678 #4 rwrw..r2"})
void extractHashtagNames_extractedHashtagsShouldBeEqualToArray(String str1, String str2){
        var extractedHashtags1 = UtilityService.extractHashtagNames(str1);
        var extractedHashtags2 = UtilityService.extractHashtagNames(str2);

        assertArrayEquals(extractedHashtags1.toArray(), extractedHashtags2.toArray());
        assertEquals(extractedHashtags1.size(), extractedHashtags2.size());
}

}