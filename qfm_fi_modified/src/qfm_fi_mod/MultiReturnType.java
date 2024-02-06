/*
 * Author: Sharmin Akter Mim
 * Code for QFM-FI
 * */
package qfm_fi_mod;

import java.util.LinkedHashSet;


public class MultiReturnType {
	private LinkedHashSet<Taxa> partA;
	private LinkedHashSet<Taxa> partB;
	public MultiReturnType(LinkedHashSet<Taxa> partA, LinkedHashSet<Taxa> partB) {
		this.partA = partA;
		this.partB = partB;
	}
	public LinkedHashSet<Taxa> getPartA() {
		return partA;
	}
	public LinkedHashSet<Taxa> getPartB() {
		return partB;
	}
	



}
