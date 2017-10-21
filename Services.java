import java.util.*;
import java.io.*;
import java.lang.*;

public class Services {
	static int cars = 10000;

	public static void writeDeterministic() {
		try {
			PrintWriter writer = new PrintWriter("deterministic.txt", "UTF-8");
			for (int i = 0; i < cars; i++) {
				writer.println("1.5");
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with writing the deterministic service times.");
		}
	}

	public static void writeExponential() {
		Random rng = new Random(4123563);
		try {
			PrintWriter writer = new PrintWriter("exponential.txt", "UTF-8");
			float xVal;
			for (int i = 0; i < cars; i++) {
				xVal = -1.5f * (float) (Math.log(1.f - rng.nextFloat()));
				writer.println(String.valueOf(xVal));
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with writing the exponential service times.");
		}
	}

	public static void writeHyperExp() {
		Random rng = new Random(789064896);
		try {
			PrintWriter writer = new PrintWriter("hyperExp.txt", "UTF-8");
			float xVal;
			for (int i = 0; i < cars; i++) {
				if (rng.nextFloat() < 0.5f) {
					xVal = -1.f * (float) (Math.log(1.f - rng.nextFloat()));
				} else {
					xVal = -2.f * (float) (Math.log(1.f - rng.nextFloat()));
				}
				writer.println(String.valueOf(xVal));
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with writing the hyper-exponential service times.");
		}
	}

	public static void writeCorrelated() {
		Random rng = new Random(923361568);
		try {
			PrintWriter writer = new PrintWriter("correlated.txt", "UTF-8");
			float prevVal = 1.5f;
			float xVal;
			for (int i = 0; i < cars; i++) {
				float check = rng.nextFloat();
				if (check < 0.2f) {
					writer.println(String.valueOf(prevVal));
				} else {
					xVal = -2.f * (float) (Math.log(1.f - rng.nextFloat()));
					writer.println(String.valueOf(xVal));
					prevVal = xVal;
				}
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with writing the correlated service times.");
		}
	}

	public static void writeServices() {
		writeDeterministic();
		writeExponential();
		writeHyperExp();
		writeCorrelated();
	}
}