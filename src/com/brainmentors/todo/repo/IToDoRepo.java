package com.brainmentors.todo.repo;

import java.io.IOException;
import java.util.ArrayList;

import com.brainmentors.todo.dto.ToDoDTO;

public interface IToDoRepo {

	public boolean addTask(ArrayList<ToDoDTO> todo) throws IOException;
	
	public ArrayList<ToDoDTO> printTasks() throws IOException, ClassNotFoundException;

}
