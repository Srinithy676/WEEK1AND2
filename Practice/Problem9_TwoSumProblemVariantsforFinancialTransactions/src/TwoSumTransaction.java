import java.util.*;

class Transaction {
    int id;
    int amount;

    Transaction(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }
}

public class TwoSumTransaction {
    public static void findTwoSum(Transaction[] transactions, int target) {
        Map<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                System.out.println("Pair found: ID " + map.get(complement).id + " and ID " + t.id);
                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No pair found");
    }

    public static void main(String[] args) {
        Transaction[] transactions = {
                new Transaction(1, 500),
                new Transaction(2, 300),
                new Transaction(3, 200),
                new Transaction(4, 700)
        };

        findTwoSum(transactions, 500);
    }
}
