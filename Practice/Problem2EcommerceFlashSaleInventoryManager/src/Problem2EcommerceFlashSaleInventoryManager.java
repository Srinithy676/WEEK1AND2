import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class FlashSaleInventoryManager {
    private ConcurrentHashMap<String, AtomicInteger> stockMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Queue<Long>> waitingList = new ConcurrentHashMap<>();
    public void addProduct(String productId, int stock) {
        stockMap.put(productId, new AtomicInteger(stock));
        waitingList.put(productId, new ConcurrentLinkedQueue<>());
    }
    public String checkStock(String productId) {
        AtomicInteger stock = stockMap.get(productId);
        if (stock == null) {
            return "Product not found";
        }
        return stock.get() + " units available";
    }
    public synchronized String purchaseItem(String productId, long userId) {
        AtomicInteger stock = stockMap.get(productId);
        if (stock == null) {
            return "Product not found";
        }
        if (stock.get() > 0) {
            int remaining = stock.decrementAndGet();
            return "Success, " + remaining + " units remaining";
        } else {
            Queue<Long> queue = waitingList.get(productId);
            queue.add(userId);
            return "Added to waiting list, position #" + queue.size();
        }
    }
}
public class Problem2EcommerceFlashSaleInventoryManager{
    public static void main(String[] args) {
        FlashSaleInventoryManager manager = new FlashSaleInventoryManager();
        manager.addProduct("IPHONE15_256GB", 100);
        System.out.println(manager.checkStock("IPHONE15_256GB"));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890));
        for(int i = 0; i < 100; i++){
            manager.purchaseItem("IPHONE15_256GB", i);
        }
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99999));
    }
}
