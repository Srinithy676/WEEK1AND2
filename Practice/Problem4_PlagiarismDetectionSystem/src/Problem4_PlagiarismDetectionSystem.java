import java.util.*;

public class Problem4_PlagiarismDetectionSystem {

    private int n;
    private HashMap<String, Set<String>> index = new HashMap<>();
    private HashMap<String, List<String>> documentNgrams = new HashMap<>();

    public Problem4_PlagiarismDetectionSystem(int n) {
        this.n = n;
    }

    private List<String> generateNgrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                sb.append(words[i + j]);
                if (j < n - 1) sb.append(" ");
            }
            grams.add(sb.toString());
        }

        return grams;
    }

    public void addDocument(String docId, String text) {
        List<String> grams = generateNgrams(text);
        documentNgrams.put(docId, grams);

        for (String gram : grams) {
            index.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }

    public void analyzeDocument(String docId) {
        List<String> grams = documentNgrams.get(docId);
        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {
            Set<String> docs = index.getOrDefault(gram, new HashSet<>());
            for (String other : docs) {
                if (!other.equals(docId)) {
                    matchCount.put(other, matchCount.getOrDefault(other, 0) + 1);
                }
            }
        }

        System.out.println("Extracted " + grams.size() + " n-grams");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {
            String otherDoc = entry.getKey();
            int matches = entry.getValue();
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches + " matching n-grams with \"" + otherDoc + "\"");
            System.out.println("Similarity: " + String.format("%.1f", similarity) + "%");
        }
    }

    public static void main(String[] args) {

        Problem4_PlagiarismDetectionSystem system = new Problem4_PlagiarismDetectionSystem(5);

        system.addDocument("essay_089.txt",
                "machine learning improves systems by learning from data and patterns");

        system.addDocument("essay_092.txt",
                "machine learning improves systems by learning from data and patterns in modern ai research");

        system.addDocument("essay_123.txt",
                "machine learning improves systems by learning from data and patterns used in many applications");

        system.analyzeDocument("essay_123.txt");
    }
}