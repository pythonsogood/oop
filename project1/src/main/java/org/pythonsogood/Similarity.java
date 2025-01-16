package org.pythonsogood;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class Similarity {
	public Similarity() {}

	public double similarity(String s1, String s2) {
		NormalizedLevenshtein l = new NormalizedLevenshtein();
		return l.similarity(s1, s2);
	}
}
