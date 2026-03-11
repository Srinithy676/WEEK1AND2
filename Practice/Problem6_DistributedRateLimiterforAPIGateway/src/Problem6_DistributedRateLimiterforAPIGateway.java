import java.util.HashMap;
import java.util.Map;

class ClientInfo {
    int count;
    long windowStart;

    ClientInfo(int count, long windowStart) {
        this.count = count;
        this.windowStart = windowStart;
    }
}

public class Problem6_DistributedRateLimiterforAPIGateway {
    private static final int LIMIT = 1000;
    private static final long WINDOW_SIZE = 3600 * 1000;

    private Map<String, ClientInfo> clientMap = new HashMap<>();

    public boolean checkRateLimit(String clientId) {
        long now = System.currentTimeMillis();

        if (!clientMap.containsKey(clientId)) {
            clientMap.put(clientId, new ClientInfo(1, now));
            return true;
        }

        ClientInfo info = clientMap.get(clientId);

        if (now - info.windowStart >= WINDOW_SIZE) {
            info.count = 1;
            info.windowStart = now;
            return true;
        }

        if (info.count < LIMIT) {
            info.count++;
            return true;
        }

        return false;
    }

    public void getRateLimitStatus(String clientId) {
        if (!clientMap.containsKey(clientId)) {
            System.out.println("No requests yet.");
            return;
        }

        ClientInfo info = clientMap.get(clientId);
        System.out.println("Used: " + info.count);
        System.out.println("Remaining: " + (LIMIT - info.count));
    }

    public static void main(String[] args) {
        Problem6_DistributedRateLimiterforAPIGateway rl = new Problem6_DistributedRateLimiterforAPIGateway();

        System.out.println(rl.checkRateLimit("abc123"));
        System.out.println(rl.checkRateLimit("abc123"));
        rl.getRateLimitStatus("abc123");
    }
}