import java.util.*;
import java.io.*;
import java.lang.*;

public class SingleServerQueue {
	public String[] arrivals = new String[4];;
	public String[] services = new String[4];;

	public SingleServerQueue(){
		arrivals[0] = "Arrivals0.txt";
		arrivals[1] = "Arrivals1.txt";
		arrivals[2] = "Arrivals2.txt";
		arrivals[3] = "Arrivals3.txt";

		services[0] = "deterministic.txt";
		services[1] = "exponential.txt";
		services[2] = "hyperExp.txt";
		services[3] = "correlated.txt";
	}

	//as interpreted from Professor Carey Williamson's SSQ example
	public void readData(String arrivalFile, String serviceFile, int indexI) {
		String[] temp = serviceFile.split(".txt");

		arrivalFile = System.getProperty("user.dir") + "/" + arrivalFile;
		serviceFile = System.getProperty("user.dir") + "/" + serviceFile;

		long carNum = 0;			// car being analyzed
		double arrival = 0.0;		// arrival time
		double wait = 0.0;				// DELAY IN QUEUE (WHAT WE'RE EXAMINING)
		double service = 0.0;				// service time
		double sojourn = 0.0;				// waiting + service
		double departure = 0.0;		// departure time
		double variance = 0.0;

		double totalWait = 0.0;		// sum of time spent waiting in queue
		double totalSojourn = 0.0;	// sum of time spent not speeding on Highway 1
		double totalService = 0.0;	// total amount of servicing
		double totalVariance = 0.0;;

		try {
			RandomAccessFile arrivalData = new RandomAccessFile(arrivalFile, "r");
			RandomAccessFile serviceData = new RandomAccessFile(serviceFile, "r");

			String str1, str2;

			while (((str1 = arrivalData.readLine()) != null) && ((str2 = serviceData.readLine()) != null)) {
				carNum++;

				arrival = Double.parseDouble(str1);
				if (arrival < departure) {
					wait = departure - arrival;							// delay in queue
				} else {
					wait = 0.0;											// no delay
				}

				variance = wait * wait;
				
				service = Double.parseDouble(str2);
				sojourn = wait + service;
				departure = arrival + sojourn;							// time of departure

				totalWait += wait;
				totalSojourn += sojourn;
				totalService += service;
				totalVariance += variance;
			}

			double waitMean = (totalWait / carNum);
			variance = (totalVariance / carNum) - (waitMean * waitMean);

			System.out.printf("\nLambda = %.2f, %s distribution:\n",(indexI * 0.05f) + 0.5f, temp[0]);
			System.out.printf("\taverage service time = %.2f\n", (totalService / carNum));
			System.out.printf("\taverage sojourn = %.2f\n", (totalSojourn / carNum));
			System.out.printf("\tAVERAGE WAIT IN QUEUE = %.2f\n", waitMean);
			//System.out.printf("\twait-in-queue variance = %.2f\n", variance);
			System.out.printf("\tWAIT-IN-QUEUE STANDARD DEVIATION = %.2f\n", Math.sqrt(variance));

			arrivalData.close();
			serviceData.close();

		} catch (IOException e) {
			System.out.println("Problem reading files.");
		}
	}

	public static void main(String[] args) {
		Arrivals.writeArrivals();
		Services.writeServices();

		SingleServerQueue ssq = new SingleServerQueue();

		for (int i = 0; i < ssq.arrivals.length; i++) {
			for (int j = 0; j < ssq.services.length; j++) {
				ssq.readData(ssq.arrivals[i], ssq.services[j], i);
			}
		}
	}
}