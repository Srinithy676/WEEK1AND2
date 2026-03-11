import java.util.*;

public class MultiLevelCache {
    private LinkedHashMap<String, String> l1Cache;
    private Map<String, String> l2Cache;
    private Map<String, String> l3Database;

    public MultiLevelCache() {
        l1Cache = new LinkedHashMap<String, String>(5, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 5;
            }
        };

        l2Cache = new HashMap<>();
        l3Database = new HashMap<>();

        l2Cache.put("video_123", "Video Data from L2");
        l3Database.put("video_999", "Video Data from L3");
    }

    public String getVideo(String videoId) {
        if (l1Cache.containsKey(videoId)) {
            return "L1 HIT: " + l1Cache.get(videoId);
        }

        if (l2Cache.containsKey(videoId)) {
            String data = l2Cache.get(videoId);
            l1Cache.put(videoId, data);
            return "L2 HIT, promoted to L1: " + data;
        }

        if (l3Database.containsKey(videoId)) {
            String data = l3Database.get(videoId);
            l2Cache.put(videoId, data);
            return "L3 HIT, added to L2: " + data;
        }

        return "Video not found";
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();

        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_999"));
    }
}
