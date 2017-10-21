import java.util.*;
import java.io.*;
import java.lang.*;

public class Arrivals {
	public static float nextArrivalTime(float lambda) {
		Random rng = new Random(1321566);
		return (-1.f * (float) (Math.log(1.f - rng.nextFloat()) / lambda));
	}

	public static void writeArrivals() {
		int cars = 10000;
		float previousArrival;
		PrintWriter writer;
		float lambda = 0.5f;

		for (int i = 0; i < 4; i++) {
			previousArrival = 0.f;
			String fileName = "Arrivals" + new Integer(i).toString() + ".txt";
			try {
				writer = new PrintWriter(fileName, "UTF-8");

				for (int j = 0; j < cars; j++) {
					previousArrival += nextArrivalTime(lambda);
					writer.println(previousArrival);
				}

				writer.close();
			} catch (IOException e) {
				System.out.println("Something went wrong with writing the arrivals.");
			}

			lambda += 0.05f;
		}
	}
}