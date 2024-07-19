package com.study;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 06.04.24
 */
@DisplayName("Stream api group by 테스트")
public class StreamGroupByTest {

	@Test
	void simpleGroupByTest() {
		List<String> words = Arrays.asList("apple", "banana", "apricot", "berry", "avocado");
		Map<Character, List<String>> groupedByFirstChar = words.stream()
			.collect(Collectors.groupingBy(word -> word.charAt(0)));
		groupedByFirstChar.forEach((key, value) -> System.out.println(key + ": " + value));
	}
}
