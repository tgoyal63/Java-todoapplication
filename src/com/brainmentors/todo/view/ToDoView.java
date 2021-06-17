package com.brainmentors.todo.view;

import static com.brainmentors.todo.utils.Constants.ADD_TASK;
import static com.brainmentors.todo.utils.Constants.DELETED;
import static com.brainmentors.todo.utils.Constants.EXIT;
import static com.brainmentors.todo.utils.Constants.PRINT_TASK;
import static com.brainmentors.todo.utils.Constants.DELETE_TASK;
import static com.brainmentors.todo.utils.Constants.SEARCH_TASK;
import static com.brainmentors.todo.utils.Constants.UPDATE_TASK;
import static com.brainmentors.todo.utils.MessageReader.getValue;
import static com.brainmentors.todo.utils.Utilities.scanner;

import java.io.EOFException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.brainmentors.todo.dto.ToDoDTO;
import com.brainmentors.todo.repo.IToDoRepo;
import com.brainmentors.todo.repo.ToDoRepo;
import com.brainmentors.todo.utils.Utilities;

public class ToDoView {
	private static String[] headers = { "Name", "Description", "Created at", "Modified at", "To be Completed By",
			"Status" };

	public static boolean updateFile(ArrayList<ToDoDTO> tasks) {
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			return repo.addTask(tasks);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void addTask() {
		System.out.println(getValue("input.taskname"));
		String name = scanner.nextLine();
		System.out.println(getValue("input.taskdesc"));
		String desc = scanner.nextLine();
		System.out.println(getValue("input.taskcompletion"));
		String completeTime = scanner.nextLine();
		Date completionTime = null;
		try {
			completionTime = Utilities.convertStringToDate(completeTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		var todo = new ToDoDTO(name, desc, completionTime);
		String result = getValue("record.notadded");

		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = null;

			try {
				tasks = repo.printTasks(); // fetching old tasks from a file
			} catch (EOFException e) {
				System.out.println(getValue("notasks"));
			}
			if (tasks != null) {
				tasks.add(todo);
			} else {
				tasks = new ArrayList<>();
				tasks.add(todo);
			}
			result = repo.addTask(tasks) ? getValue("record.added") : getValue("record.notadded");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	private static void printAllTask() {

		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks;

			try {
				tasks = repo.printTasks(); // fetching old tasks from a file
			} catch (EOFException e) {
				System.out.println(getValue("notasks"));
				return;
			}
			var clt = Utilities.getCltInstance();
			clt.setShowVerticalLines(true);
			clt.setHeaders(headers);
			for (ToDoDTO task : tasks) {
				System.out.println(task);
			}
			System.out.println("Total tasks: " + tasks.size());
			clt.print();
			Utilities.deleteCltInstance();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void searchTask() {
		System.out.println(getValue("search.taskname"));
		String taskName = scanner.nextLine();
		ToDoDTO taskToSearch = null;
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = null;

			try {
				tasks = repo.printTasks(); // fetching old tasks from a file
			} catch (EOFException e) {
				System.out.println(getValue("notasks"));
				return;
			}
			for (ToDoDTO task : tasks) {
				if (task.getName().equals(taskName) && !task.getStatus().equals(DELETED)) {
					taskToSearch = task;
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (taskToSearch == null) {
			System.out.println(getValue("tasknotfound"));
			return;
		}

		var clt = Utilities.getCltInstance();
		clt.setShowVerticalLines(true);
		clt.setHeaders(headers);
		System.out.println(taskToSearch);
		clt.print();
		Utilities.deleteCltInstance();
	}

	public static void updateName(ToDoDTO task) {
		System.out.println(getValue("input.taskname"));
		task.setName(scanner.nextLine());
	}

	public static void updateDesc(ToDoDTO task) {
		System.out.println(getValue("input.taskdesc"));
		task.setDesc(scanner.nextLine());
	}

	public static void updateDeadline(ToDoDTO task) {
		System.out.println(getValue("input.taskcompletion"));
		Date completionTime = null;
		try {
			completionTime = Utilities.convertStringToDate(scanner.nextLine());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		task.setCompletionTime(completionTime);
	}

	public static void updateStatus(ToDoDTO task) {
		System.out.println(getValue("input.taskstatus"));
		task.setStatus(scanner.nextLine());
	}

	public static void updateTask() {
		System.out.println(getValue("update.taskname"));
		String taskName = scanner.nextLine();
		ToDoDTO taskToSearch = null;
		ArrayList<ToDoDTO> tasks = null;
		try {
			IToDoRepo repo = ToDoRepo.getInstance();

			try {
				tasks = repo.printTasks(); // fetching old tasks from a file
			} catch (EOFException e) {
				System.out.println(getValue("notasks"));
				return;
			}
			for (ToDoDTO task : tasks) {
				if (task.getName().equals(taskName) && !task.getStatus().equals(DELETED)) {
					taskToSearch = task;
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (taskToSearch == null) {
			System.out.println(getValue("tasknotfound"));
			return;
		}

		var clt = Utilities.getCltInstance();
		clt.setShowVerticalLines(true);
		clt.setHeaders(headers);
		System.out.println(taskToSearch);
		System.out.println("Current Details: ");
		clt.print();
		Utilities.deleteCltInstance();

		System.out.println("What do you want to update? ");
		System.out.println("1. Name");
		System.out.println("2. Description");
		System.out.println("3. Completion Deadline");
		System.out.println("4. Status");
		System.out.println(getValue("choice"));
		var choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1:
			updateName(taskToSearch);
			break;
		case 2:
			updateDesc(taskToSearch);
			break;
		case 3:
			updateDeadline(taskToSearch);
			break;
		case 4:
			updateStatus(taskToSearch);
			break;
		default:
			System.out.println("Invalid Choice... Please try choice");
		}
		taskToSearch.setModifiedTime(new Date());

		clt = Utilities.getCltInstance();
		clt.setShowVerticalLines(true);
		clt.setHeaders(headers);
		System.out.println(taskToSearch);
		System.out.println("Updated Details: ");
		clt.print();
		Utilities.deleteCltInstance();
		updateFile(tasks);
	}

	public static void deleteTask() {

		System.out.println(getValue("delete.taskname"));
		String taskName = scanner.nextLine();
		ToDoDTO taskToSearch = null;
		ArrayList<ToDoDTO> tasks = null;
		try {
			IToDoRepo repo = ToDoRepo.getInstance();

			try {
				tasks = repo.printTasks(); // fetching old tasks from a file
			} catch (EOFException e) {
				System.out.println(getValue("notasks"));
				return;
			}
			for (ToDoDTO task : tasks) {
				if (task.getName().equals(taskName)) {
					taskToSearch = task;
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (taskToSearch == null) {
			System.out.println(getValue("tasknotfound"));
			return;
		}

		var clt = Utilities.getCltInstance();
		clt.setShowVerticalLines(true);
		clt.setHeaders(headers);
		System.out.println(taskToSearch);
		System.out.println("Current Details: ");
		clt.print();
		Utilities.deleteCltInstance();

		System.out.println("Do you want to delete? (Y/N): ");
		String confirm=scanner.next();
		scanner.nextLine();
		if(confirm.equals("Y")) {
			taskToSearch.setModifiedTime(new Date());
			taskToSearch.setStatus(DELETED);
			System.out.println("Task Deleted");
		}
		else {
			System.out.println("Task Deletion Cancelled");
		}

		Utilities.deleteCltInstance();
		updateFile(tasks);

	}

	private static void printInputMenu() {
		System.out.println(getValue("addtask"));
		System.out.println(getValue("deletetask"));
		System.out.println(getValue("updatetask"));
		System.out.println(getValue("searchtask"));
		System.out.println(getValue("printtask"));
		System.out.println(getValue("exittask"));
		System.out.println(getValue("choice"));
	}

	public static void main(String[] args) {
		var choice = 0;
		do {
			printInputMenu();
			choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case ADD_TASK:
				addTask();
				break;
			case DELETE_TASK:
				deleteTask();
				break;
			case UPDATE_TASK:
				updateTask();
				break;
			case SEARCH_TASK:
				searchTask();
				break;
			case PRINT_TASK:
				printAllTask();
				break;
			case EXIT:
				System.out.println("Thanks for using the application.");
				break;
			default:
				System.out.println("Incorrect Choice! Please try again...");
			}
		} while (choice != 6);
		scanner.close();
	}

}
