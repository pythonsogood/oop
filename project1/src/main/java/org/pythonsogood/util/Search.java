package org.pythonsogood.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class Search {
	private static Search instance;
	private NormalizedLevenshtein levenshtein = new NormalizedLevenshtein();

	public static Search getInstance() {
		if (instance == null) {
			instance = new Search();
		}
		return instance;
	}

	public List<String> search(String query, List<String> items, Double score_cutoff) {
		if (items.size() == 0) {
			return items;
		}

		HashMap<String, Double> similarity_map = new HashMap<>();
		for (String item : items) {
			similarity_map.put(item, this.partial_ratio_similarity(query, item));
		}

		ArrayList<String> result = new ArrayList<>(items);

		result.sort((a, b) -> similarity_map.get(b).compareTo(similarity_map.get(a)));

		for (int i = 0; i<result.size(); i++) {
			if (similarity_map.get(result.get(i)) < score_cutoff) {
				result.remove(i);
				i--;
			}
		}

		return result;
	}

	public List<String> search(String query, HashMap<String, List<String>> items, Double score_cutoff) {
		if (items.size() == 0) {
			return new ArrayList<>(items.keySet());
		}

		HashMap<String, Double> similarity_map = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : items.entrySet()) {
			String item = entry.getKey();
			List<String> values = entry.getValue();
			Double max_score = 0.0;
			for (String value : values) {
				Double score = this.partial_ratio_similarity(query, value);
				if (score > max_score) {
					max_score = score;
				}
			}
			similarity_map.put(item, max_score);
		}

		ArrayList<String> result = new ArrayList<>(items.keySet());

		result.sort((a, b) -> similarity_map.get(b).compareTo(similarity_map.get(a)));

		for (int i = 0; i<result.size(); i++) {
			if (similarity_map.get(result.get(i)) < score_cutoff) {
				result.remove(i);
				i--;
			}
		}

		return result;
	}

	public List<String> search(String query, List<String> items) {
		return search(query, items, 35.0);
	}

	public List<String> search(String query, HashMap<String, List<String>> items) {
		return search(query, items, 35.0);
	}

	private Double similarity(String s1, String s2) {
		return levenshtein.similarity(s1, s2) * 100.0;
	}

	private Double partial_ratio_similarity(String s1, String s2) {
		/*
			Algorithm taken from https://github.com/rapidfuzz/RapidFuzz/blob/main/src/rapidfuzz/fuzz_py.py#L116-L179
		*/
		int len1 = s1.length();
		int len2 = s2.length();

		if (len1 > len2) {
			return this.partial_ratio_similarity(s2, s1);
		}

		Double score = 0.0;

		Set<Character> s1_char_set = new HashSet<>();
		s1.chars().forEach(c -> s1_char_set.add((char)c));

		for (int i=1; i<len1; i++) {
			char substr_last = s2.charAt(i-1);
			if (!s1_char_set.contains(substr_last)) {
				continue;
			}

			Double ls_ratio = this.similarity(s1, s2.substring(0, i));

			if (ls_ratio > score) {
				score = ls_ratio;
				if (score >= 100.0) {
					return score;
				}
			}
		}

		for (int i=0; i<len2-len1; i++) {
			char substr_last = s2.charAt(i + len1 - 1);
			if (!s1_char_set.contains(substr_last)) {
				continue;
			}

			Double ls_ratio = this.similarity(s1, s2.substring(i, i + len1));

			if (ls_ratio > score) {
				score = ls_ratio;
				if (score >= 100.0) {
					return score;
				}
			}
		}

		for (int i=len2-len1; i<len2; i++) {
			char substr_first = s2.charAt(i);
			if (!s1_char_set.contains(substr_first)) {
				continue;
			}

			Double ls_ratio = this.similarity(s1, s2.substring(i));

			if (ls_ratio > score) {
				score = ls_ratio;
				if (score >= 100.0) {
					return score;
				}
			}
		}

		return score;
	}
}
