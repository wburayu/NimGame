
import java.util.*;

/**
 * Welela Burayu
 * 04-28-2022
 * CS372 - A.I
 * Project 5: Graph Warmup, the program asks the user for a filename, then reads the file,
 * and then let the user type in the identifier for as many locations as they want. It will then print the roads that are directly
 * connected to that location,along with their speed limits, names, and how many seconds it
 * will take to drive that particular road segment.
 * I have neither given nor received unauthorized aid on this program.
 */

public class Nim{

	public static Scanner scanner;
	public static void main(String[] args)
	{
		scanner=new Scanner(System.in);//The scanner
		//System.out.println();
		int p0,p1,p2;
		Long games;
		System.out.println("Welcome to the Nim Game!");
		System.out.println("Number in pile 0?");
		p0=scanner.nextInt();
		System.out.println("Number in pile 1?");
		p1=scanner.nextInt();
		System.out.println("Number in pile 2?");
		p2=scanner.nextInt();
		System.out.println("\nNumber of games to simulate?");
		games=scanner.nextLong();

		System.out.println("\nInitial board is "+p0+"-"+p1+"-"+p2+", simulating "+games+" games.");
		String board="A"+p0+""+p1+""+p2+"99";//initializes the board

		QLearn qlearn=new QLearn(games,board);//calls the Qlearn class
		





		




	}

}