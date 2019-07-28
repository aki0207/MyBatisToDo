package com.example.MyBatisToDo.app.todo;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class TodoForm implements Serializable {
	// 入力チェックルールをグループ化するためのインタフェースを作成する。
	public static interface TodoCreate {};
	public static interface TodoFinish {};
	public static interface TodoDelete {};

	private static final long serialVersionUID = 1L;
	// todoIdは完了処理で使用するプロパティである。
	// そのため、@NotNullアノテーションのgroups属性には、完了処理用の入力チェックルールである事を示すTodoFinishインタフェースを指定する。
	@NotNull(groups = { TodoFinish.class,TodoDelete.class })
	private String todoId;


	// todoTitleは新規作成処理で使用するプロパティである。
	// そのため、@NotNullアノテーションと@Sizeアノテーションのgroups属性には、新規作成処理用の入力チェックルールである事を示すTodoCreateインタフェースを指定する。
	@NotNull(groups = { TodoCreate.class })
	@Size(min = 1, max = 30, groups = { TodoCreate.class })
	private String todoTitle;

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}



	public String getTodoTitle() {
		return todoTitle;
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}

}
