import java.io.*;
import java.util.*;

public class FastSubsetSum {

    /**
     * FastSubsetSum algorithm from Erickson's Algorithms (2019).
     * Uses a set-based dynamic programming approach.
     * Returns true if there is a subset of X with sum exactly T.
     */
    public static boolean fastSubsetSum(int[] X, int T) {
        Set<Integer> S = new HashSet<>();
        S.add(0);
        for (int x : X) {
            Set<Integer> newS = new HashSet<>(S);
            for (int s : S) {
                int val = s + x;
                if (val <= T) {
                    newS.add(val);
                }
            }
            S = newS;
        }
        return S.contains(T);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java FastSubsetSum <input_file>");
            System.exit(1);
        }

        String inputFile = args[0];

        Scanner scanner;
        try {
            scanner = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            System.exit(1);
            return;
        }

        int n = scanner.nextInt();
        int[] X = new int[n];
        for (int i = 0; i < n; i++) {
            X[i] = scanner.nextInt();
        }
        int T = scanner.nextInt();
        scanner.close();

        boolean result = fastSubsetSum(X, T);

        new File("outputs").mkdirs();

        String inputFilename = new File(inputFile).getName();
        String outputFilename = "outputs/output " + inputFilename;

        PrintWriter writer = new PrintWriter(new FileWriter(outputFilename));
        writer.println(result ? "TRUE" : "FALSE");
        writer.close();

        System.out.println(result ? "TRUE" : "FALSE");
    }
}
