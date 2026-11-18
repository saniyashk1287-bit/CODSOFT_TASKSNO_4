import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple Quiz with a 30 second timer.
 * One timer for the whole quiz. When it ends, the result is shown.
 */
public class Quiz {

    static final int TIME_LIMIT_SECONDS = 30;

    // Questions, options and correct answers (option number 1-4)
    static String[] questions = {
        "Which keyword is used to inherit a class in Java?",
        "Which of these is NOT a primitive data type?",
        "Which collection does NOT allow duplicates?",
        "Which method is the entry point of a Java program?"
    };
    static String[][] options = {
        {"implements", "extends", "inherits", "super"},
        {"int", "boolean", "String", "char"},
        {"ArrayList", "LinkedList", "HashSet", "Vector"},
        {"start()", "run()", "init()", "main()"}
    };
    static int[] correct = {2, 3, 3, 4};

    static int[] chosen = new int[questions.length]; // 0 = not answered
    static boolean finished = false;

    // Show the final score and a summary (runs only once)
    static synchronized void showResult(boolean timeUp) {
        if (finished) return;
        finished = true;

        if (timeUp) {
            System.out.println("\n\nTime's up!");
        }

        int score = 0;
        System.out.println("\n===== RESULT =====");
        for (int i = 0; i < questions.length; i++) {
            String status;
            if (chosen[i] == 0) {
                status = "Not answered";
            } else if (chosen[i] == correct[i]) {
                status = "Correct";
                score++;
            } else {
                status = "Wrong";
            }
            System.out.println("Q" + (i + 1) + ": " + status
                    + " (answer: " + options[i][correct[i] - 1] + ")");
        }
        System.out.println("Score: " + score + " / " + questions.length);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== QUIZ === You have " + TIME_LIMIT_SECONDS + " seconds!");
        System.out.println("Type the option number and press Enter.\n");

        // The one timer: after 10 seconds show the result and stop
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                showResult(true);
                System.exit(0);
            }
        }, TIME_LIMIT_SECONDS * 1000L);

        for (int i = 0; i < questions.length; i++) {
            if (finished) return;
            System.out.println("Q" + (i + 1) + ". " + questions[i]);
            for (int j = 0; j < 4; j++) {
                System.out.println("   " + (j + 1) + ". " + options[i][j]);
            }
            System.out.print("Answer: ");

            String line = sc.nextLine().trim();
            if (line.matches("[1-4]")) {
                chosen[i] = Integer.parseInt(line);
            }
            System.out.println();
        }

        timer.cancel();
        showResult(false);
    }
}