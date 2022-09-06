package app;

import ui.Ui;
import objects.Task;
import utils.Parser;
import utils.Storage;
import utils.TaskList;

import java.io.IOException;
import java.util.List;

public class Duke {
    private static TaskList taskList;
    private static List<Task> tasks;
    private static Storage storage;
    private static Parser parser;

    public Duke() {
        taskList = new TaskList();
        tasks = taskList.getTasks();
        storage = new Storage();
        parser = new Parser();
    }

    public static void loadDuke() throws IOException {
        assert tasks.size() == 0;
        storage.loadTasks(tasks);
    }

    public static void saveDuke() throws IOException {
        storage.saveTasks(tasks);
    }

    public String getResponse(String input) {
        return "Duck says: \n\n" + parser.parseCommand(tasks, input);
    }
}