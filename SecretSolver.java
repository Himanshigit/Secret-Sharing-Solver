import java.util.*;
import org.json.*;
import java.io.*;

public class SecretSharingMatrixMethod {

    public static double[] solveLinear(double[][] A, double[] Y) {
        int n = Y.length;

        // Forward elimination
        for (int i = 0; i < n; i++) {
            // Pivoting
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[max][i])) {
                    max = j;
                }
            }

            // Swap rows
            double[] tempRow = A[i];
            A[i] = A[max];
            A[max] = tempRow;

            double tmpVal = Y[i];
            Y[i] = Y[max];
            Y[max] = tmpVal;

            // Eliminate
            for (int j = i + 1; j < n; j++) {
                double factor = A[j][i] / A[i][i];
                Y[j] -= factor * Y[i];

                for (int k = i; k < n; k++) {
                    A[j][k] -= factor * A[i][k];
                }
            }
        }

        // Back substitution
        double[] X = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = Y[i];
            for (int j = i + 1; j < n; j++) {
                sum -= A[i][j] * X[j];
            }
            X[i] = sum / A[i][i];
        }

        return X;
    }

    public static void solveMatrix(JSONObject testCase) {
        try {
            JSONObject keys = testCase.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k"); // degree + 1

            // Parse points
            List<Integer> xList = new ArrayList<>();
            List<Integer> yList = new ArrayList<>();

            for (String key : testCase.keySet()) {
                if (key.equals("keys")) continue;

                int x = Integer.parseInt(key);
                JSONObject point = testCase.getJSONObject(key);
                int base = Integer.parseInt(point.getString("base"));
                String value = point.getString("value");
                int y = Integer.parseInt(value, base);

                xList.add(x);
                yList.add(y);
            }

            // Use first k points
            List<Integer> xPoints = xList.subList(0, k);
            List<Integer> yPoints = yList.subList(0, k);

            double[][] A = new double[k][k];
            double[] Y = new double[k];

            for (int i = 0; i < k; i++) {
                int xi = xPoints.get(i);
                Y[i] = yPoints.get(i);

                for (int j = 0; j < k; j++) {
                    A[i][j] = Math.pow(xi, k - j - 1); // descending powers
                }
            }

            double[] coefficients = solveLinear(A, Y);
            double c = coefficients[k - 1]; // constant term is last
            System.out.println("Secret (constant term c) = " + c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader("input.json"));
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JSONObject input = new JSONObject(sb.toString());
            JSONArray testcases = input.getJSONArray("testcases");

            for (int i = 0; i < testcases.length(); i++) {
                System.out.println("Test Case " + (i + 1));
                solveMatrix(testcases.getJSONObject(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
