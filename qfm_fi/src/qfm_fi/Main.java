/*
 * Author: Sharmin Akter Mim
 * Code for QFM-FI
 * */
package qfm_fi;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String quartetType = "2";
		String inputFile = "g:/Research/Dataset_Simulated/37-taxon/noscale.25g.500b/R2/weighted_quartets";
		String outputFile = "IO/output_org.txt";
		System.out.println("args.length = "+ args.length);
		
		if (args.length >= 1) {
			inputFile = args[0];
		}

		if (args.length >= 2) {
			quartetType = args[1];
		}

		if (args.length >= 3) {
			outputFile = args[2];
		}
		
		String s = "";
		switch (quartetType) {
		case "1":
			System.out.println("Quartet Format: QMC i.e 1,2|3,4");
			s = Routines.readQuartetQMC(inputFile);
			break;
		case "2":
			//eta change korte hobe. non weighted newick quartet er jonno ekta function likhte hobe.
			System.out.println("Quartet Format: Newick i.e ((1,2),(3,4)); Frequency");
			//s = Routines.readNewickQuartet(args[0]);
			s = Routines.newickQuartetWeightAsFrequency(inputFile);
			break;
		case "3":
			System.out.println("Quartet Format: QMC, Quartet Generation Method: SVDquartet(qweight = none) i.e 1,2|3,4:1");
			s = Routines.readSVDquartet(inputFile);
			break;
		case "4":
			System.out.println("Quartet Format: Newick i.e ((1,2),(3,4)); Frequency");
			s = Routines.newickQuartetWeightAsFrequency(inputFile);
			break;
		default:
			s = Routines.readQuartetQMC(inputFile);
			break;
		}

		
		//String s= Routines.readQuartetQMC("r1_qrt_n500_c70_qf2.qrt");
		//String s= Routines.readQuartetQMC(args[0]);
		//String s = Routines.newickQuartetWeightAsFrequency(args[0]);
		
//		String s= Routines.readQuartetQMC("27_ecoli.txt");
//		System.out.println(s);
//		try(BufferedWriter bw = new BufferedWriter(new FileWriter("gene101Out.txt"))) {//args[1] is output file
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {//args[1] is output file
			bw.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Total Running Time : "+ estimatedTime + " milli seconds");

		
	}

}
