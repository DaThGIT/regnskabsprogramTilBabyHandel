import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Udregner {
    // data structure for holding the expenses, divided between the respektive buyers
    private static Map<String, Map<String, BigDecimal>> ds = new HashMap<>();
    public static void main(String[] args) throws Exception {
        computeData();
    }
    
    private static void computeData(){
        readInput();
        Map<String, BigDecimal> results = computeSums();
        saveComputedData(results);
    }   
    
    
    private static void saveComputedData(Map<String, BigDecimal> results){
        Set<String> keySet = results.keySet();
        BigDecimal total = results.get("Total");
        StringBuilder sb = new StringBuilder();

        sb.append("\n ******* How much DKK has been used on baby merchandise? ******* \n         Total amount used on baby merchandise: " + total+ "\n");
        for (String key: keySet) {
            if (!key.contains("Total")){
                sb.append("\n The total sum used by " + key + " is " + results.get(key) + "\n"); 
            }
        }        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("resultat"));
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("kunne ikke finde filen til resultater");
            e.printStackTrace();
        }
        
    }

    private static Map<String, BigDecimal> computeSums(){
        Map<String, BigDecimal> res = new HashMap<>(); 
        Set<String> keySet = ds.keySet();     
        BigDecimal sum = new BigDecimal(0);
        for (String key: keySet) {
            Map<String, BigDecimal> expenseMap = ds.get(key);
            for (String expense: expenseMap.keySet()) {
                BigDecimal currentExpense = expenseMap.get(expense);
                sum = sum.add(currentExpense);
            }
            res.put(key, sum);
            sum = new BigDecimal(0);
        }
        String keyTotal = "Total";
        BigDecimal total = new BigDecimal(0);
        for (String expense: res.keySet()) {
            BigDecimal currentExpense = res.get(expense);
            total = total.add(currentExpense);         
        }
        res.put(keyTotal, total);
        total = new BigDecimal(0);
        return res;
    }

    private static void mapDataToDS(String expense, String amount, String key){
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(amount));
        Map<String, BigDecimal> expenseMap = new HashMap<>();
        expenseMap.put(expense, price);
        if(!ds.containsKey(key)) {
            ds.put(key, expenseMap);     
        } else if (ds.get(key).containsKey(expense)){ 
            BigDecimal sum = ds.get(key).get(expense).add(price);
            ds.get(key).put(expense, sum);
        } else {                              
            ds.get(key).put(expense, price);        
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

