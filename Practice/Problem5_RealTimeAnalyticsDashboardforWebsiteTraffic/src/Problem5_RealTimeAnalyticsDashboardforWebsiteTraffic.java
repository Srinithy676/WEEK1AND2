
import java.util.*;

public class Problem5_RealTimeAnalyticsDashboardforWebsiteTraffic {

    private HashMap<String, Integer> pageViews = new HashMap<>();
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    private HashMap<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.computeIfAbsent(url, k -> new HashSet<>()).add(userId);

        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        System.out.println("Top Pages:");

        int rank = 1;
        while (!pq.isEmpty() && rank <= 10) {
            Map.Entry<String, Integer> entry = pq.poll();
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.getOrDefault(url, new HashSet<>()).size();

            System.out.println(rank + ". " + url + " - " + views + " views (" + unique + " unique)");
            rank++;
        }

        System.out.println();
        System.out.println("Traffic Sources:");

        int total = 0;
        for (int c : trafficSources.values()) {
            total += c;
        }

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {
            double percent = (entry.getValue() * 100.0) / total;
            System.out.println(entry.getKey() + ": " + String.format("%.1f", percent) + "%");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Problem5_RealTimeAnalyticsDashboardforWebsiteTraffic dashboard =
                new Problem5_RealTimeAnalyticsDashboardforWebsiteTraffic();

        dashboard.processEvent("/article/breaking-news", "user_123", "google");
        dashboard.processEvent("/article/breaking-news", "user_456", "facebook");
        dashboard.processEvent("/sports/championship", "user_222", "google");
        dashboard.processEvent("/sports/championship", "user_333", "direct");
        dashboard.processEvent("/sports/championship", "user_444", "google");
        dashboard.processEvent("/article/breaking-news", "user_123", "google");

        Thread.sleep(5000);

        dashboard.getDashboard();
    }
}