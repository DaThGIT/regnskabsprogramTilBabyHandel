import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Udregner {
    // data structure for holding the expenses, divided between the respektive buyers
    private static Map<String, Map<String, BigInteger>> ds = new HashMap<>();

    public static void main(String[] args) throws Exception {
        readInput();
        // compute wanted functions on data structure
        // save data to file "results" 
    }

    private static void saveData(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("resultat"));
        } catch (IOException e) {
            System.out.println("kunne ikke finde filen til resultater");
            e.printStackTrace();
        }        
    }

    private static void mapDataToDS(String expense, String amount, String key){
        BigInteger price = BigInteger.valueOf(Long.parseLong(amount));
        Map<String, BigInteger> expenseMap = new HashMap<>();
        expenseMap.put(expense, price);
        if(ds.get(key).get(expense) == null){ // first time expense
            ds.put(key, expenseMap);
        } else {                              // add to existing expense  
            BigInteger sum = ds.get(key).get(expense).add(price);
            expenseMap.put(expense, sum);
            ds.put(key, expenseMap);
        }
            
    }

    private static void readInput(){
        String splitBy = ",";
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader("udgifter"));
            while ((line = br.readLine()) != null){
                String expense, amount, key;
                String[] input = line.split(splitBy); 
                expense = input[0].trim();
                amount = input[1].trim();
                key = input[2].trim();
                mapDataToDS(expense, amount, key);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Kunne ikke finde filen med udgifter");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

