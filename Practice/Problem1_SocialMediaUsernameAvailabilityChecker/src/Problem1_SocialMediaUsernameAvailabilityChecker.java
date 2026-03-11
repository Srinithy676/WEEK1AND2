import java.util.*;

public class Problem1_SocialMediaUsernameAvailabilityChecker{

    static HashMap<String, Integer> users = new HashMap<>();
    static HashMap<String, Integer> attempts = new HashMap<>();

    public static boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);

    }

    public static List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String alt = username.replace("_", ".");
        if (!users.containsKey(alt)) {
            suggestions.add(alt);
        }

        return suggestions;
    }

    public static String getMostAttempted() {
        String mostAttempted = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        users.put("john_doe", 101);
        users.put("admin", 102);
        users.put("alex", 103);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username to check: ");
        String username = sc.nextLine();

        if (checkAvailability(username)) {
            System.out.println("Username Available");
        } else {
            System.out.println("Username Taken");
            System.out.println("Suggestions: " + suggestAlternatives(username));
        }

        System.out.println("Most Attempted Username: " + getMostAttempted());
    }
}