package com.brainmentors.todo.repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.brainmentors.todo.dto.ToDoDTO;
import com.brainmentors.todo.utils.Constants;

// Singleton Class - Design Pattern
public class ToDoRepo implements IToDoRepo {
	private File file;

	private static ToDoRepo todoRepo; // null

	private ToDoRepo() throws IOException {
		System.out.println(Constants.PATH);
		file = new File(Constants.PATH);
		var path = Paths.get(Constants.PATH);
		Files.createDirectories(path.getParent());
		if (file.createNewFile())
			System.out.println("File is empty! Please add task.");
	}

	public static ToDoRepo getInstance() throws IOException {
		if (todoRepo == null) {
			todoRepo = new ToDoRepo();
		}
		return todoRepo;
	}

	@Override
	public boolean addTask(ArrayList<ToDoDTO> tasks) throws IOException {
		try (var fo = new FileOutputStream(file); ObjectOutputStream os = new ObjectOutputStream(fo)) {
			os.writeObject(tasks);
		}
		return true;
	}

	@Override
	public ArrayList<ToDoDTO> printTasks() throws IOException, ClassNotFoundException {
		ArrayList<ToDoDTO> list = new ArrayList<>();
		try (var fs = new FileInputStream(file); ObjectInputStream os = new ObjectInputStream(fs)) {
			list = (ArrayList<ToDoDTO>) os.readObject();
		}
		return list;
	}

}
