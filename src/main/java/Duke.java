import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    public enum Command {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT
    }

    private static List<Task> tasks = new ArrayList<>();

    /**
     * Exits the app.
     * @param input scanner object
     */
    public static void endSession(Scanner input) {
        System.out.println("Bye! See you next time!");
        input.close();
    }

    /**
     * Prints out all the added tasks.
     */
    public static void showTasks() {
        int id = 1;
        for (Task task: tasks) {
            System.out.println(id + "." + task.toString());
            id += 1;
        }
    }

    /**
     * Marks the task at the taskIndex in the list as done.
     * @param taskIndex position of the task in the list (1-indexed)
     */
    public static void markTaskAsDone(int taskIndex) {
        if (tasks.size() == 0) {
            System.out.println("You can't do that! There are no tasks added yet...");
            return;
        }
        if (taskIndex > tasks.size() || taskIndex <= 0) {
            System.out.println("There are no tasks with that index...");
            return;
        }
        Task currTask = tasks.get(taskIndex - 1); // label starting from 1
        currTask.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + currTask.toString());
    }

    /**
     * Marks the task at the taskIndex in the list as not done.
     * @param taskIndex position of the task in the list (1-indexed)
     */
    public static void markTaskAsNotDone(int taskIndex) {
        if (tasks.size() == 0) {
            System.out.println("You can't do that! There are no tasks added yet...");
            return;
        }
        if (taskIndex > tasks.size() || taskIndex <= 0) {
            System.out.println("There are no tasks with that index...");
            return;
        }
        Task currTask = tasks.get(taskIndex - 1); // label starting from 1
        currTask.markAsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + currTask.toString());
    }

    /**
     * Prints a comment on the number of tasks added so far.
     */
    public static void printNumberOfTasks() {
        if (tasks.size() == 1) {
            System.out.println("Now you have " + tasks.size() + " task in the list.");
        } else {
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    /**
     * Creates a new Todo object and adds it to the tasks list.
     * @param inputs array of input strings
     */
    public static void addTodo(String[] inputs) {
        StringBuilder todoName = new StringBuilder();
        for (int i = 1; i < inputs.length - 1; i++) {
            todoName.append(inputs[i]).append(" ");
        }
        // To prevent space at the end of the string
        todoName.append(inputs[inputs.length - 1]);

        Todo newTodo = new Todo(todoName.toString());
        tasks.add(newTodo);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTodo.toString());
        printNumberOfTasks();
    }

    /**
     * Creates a new Deadline object and adds it to the tasks list.
     * @param inputs array of input strings
     */
    public static void addDeadline(String[] inputs) {
        StringBuilder deadlineName = new StringBuilder();
        StringBuilder endDateTime = new StringBuilder();
        boolean readDateTime = false;
        for (int i = 1; i < inputs.length - 1; i++) {
            if (inputs[i].equals("/by")) {
                readDateTime = true;
                continue;
            }
            if (!readDateTime) {
                deadlineName.append(inputs[i]).append(" ");
            } else {
                endDateTime.append(inputs[i]).append(" ");
            }
        }
        // To prevent space at the end of the string
        endDateTime.append(inputs[inputs.length - 1]);

        Deadline newDeadline = new Deadline(deadlineName.toString(), endDateTime.toString());
        tasks.add(newDeadline);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newDeadline.toString());
        printNumberOfTasks();
    }

    /**
     * Creates a new Event object and adds it to the tasks list.
     * @param inputs array of input strings
     */
    public static void addEvent(String[] inputs) {
        StringBuilder eventName = new StringBuilder();
        StringBuilder periodDateTime = new StringBuilder();
        boolean readDateTime = false;
        for (int i = 1; i < inputs.length - 1; i++) {
            if (inputs[i].equals("/at")) {
                readDateTime = true;
                continue;
            }
            if (!readDateTime) {
                eventName.append(inputs[i]).append(" ");
            } else {
                periodDateTime.append(inputs[i]).append(" ");
            }
        }
        // To prevent space at the end of the string
        periodDateTime.append(inputs[inputs.length - 1]);

        Event newEvent = new Event(eventName.toString(), periodDateTime.toString());
        tasks.add(newEvent);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newEvent.toString());
        printNumberOfTasks();
    }

    /**
     * Main function of the app.
     *
     * The first string consists of the command keyword. The possible keywords are defined
     * as an Enum. If no keyword is detected, the app alerts the user.
     *
     * If a keyword is detected, an action corresponding to the keyword will be executed.
     * The descriptions or additional data written after the keyword will be parsed,
     * and the relevant actions will be executed.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello there! My name's Duck...");
        System.out.println("Please type in a command...");
        Scanner input = new Scanner(System.in);
        while (true) {
            String inputLine = input.nextLine();
            String[] inputs = inputLine.split(" ");
            String command = inputs[0];

            if (inputLine.equals(Command.BYE.name().toLowerCase())) {
                endSession(input);
                return;
            } else if (inputLine.equals(Command.LIST.name().toLowerCase())) {
                if (tasks.isEmpty()) System.out.println("You have no tasks...");
                showTasks();
            } else if (command.equals(Command.MARK.name().toLowerCase())) {
                // inputs[1] is the index number of the task to be marked
                markTaskAsDone(Integer.parseInt(inputs[1]));
            } else if (command.equals(Command.UNMARK.name().toLowerCase())) {
                // inputs[1] is the index number of the task to be unmarked
                markTaskAsNotDone(Integer.parseInt(inputs[1]));
            } else if (command.equals(Command.TODO.name().toLowerCase())) {
                addTodo(inputs);
            } else if (command.equals(Command.DEADLINE.name().toLowerCase())) {
                addDeadline(inputs);
            } else if (command.equals(Command.EVENT.name().toLowerCase())) {
                addEvent(inputs);
            } else {
                // when none of the commands match
                System.out.println("I don't get what you are saying...");
            }
        }
    }
}
