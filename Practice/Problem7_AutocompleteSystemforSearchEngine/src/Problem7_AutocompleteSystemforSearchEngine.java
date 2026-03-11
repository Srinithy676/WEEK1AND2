import java.util.*;

public class Problem7_AutocompleteSystemforSearchEngine {

    private Map<String, Integer> queryFreq = new HashMap<>();

    public void addQuery(String query) {
        queryFreq.put(query, queryFreq.getOrDefault(query, 0) + 1);
    }

    public void updateFrequency(String query) {
        queryFreq.put(query, queryFreq.getOrDefault(query, 0) + 1);
        System.out.println("Frequency: " + queryFreq.get(query));
    }

    public List<String> search(String prefix) {
        PriorityQueue<Map.Entry<String,Integer>> pq =
                new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());

        for (Map.Entry<String,Integer> entry : queryFreq.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                pq.offer(entry);
            }
        }

        List<String> result = new ArrayList<>();
        int count = 0;

        while (!pq.isEmpty() && count < 10) {
            Map.Entry<String,Integer> e = pq.poll();
            result.add(e.getKey() + " (" + e.getValue() + " searches)");
            count++;
        }

        return result;
    }

    public static void main(String[] args) {

        Problem7_AutocompleteSystemforSearchEngine ac =
                new Problem7_AutocompleteSystemforSearchEngine();

        ac.addQuery("java tutorial");
        ac.addQuery("javascript");
        ac.addQuery("java tutorial");
        ac.addQuery("java download");
        ac.addQuery("java compiler");
        ac.addQuery("java tutorial");
        ac.addQuery("java 21 features");

        List<String> suggestions = ac.search("jav");

        int rank = 1;
        for(String s : suggestions){
            System.out.println(rank + ". " + s);
            rank++;
        }

        ac.updateFrequency("java 21 features");
        ac.updateFrequency("java 21 features");
    }
}