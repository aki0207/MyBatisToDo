package com.example.MyBatisToDo.app.todo;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.Default;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import com.example.MyBatisToDo.app.todo.TodoForm.TodoCreate;
import com.example.MyBatisToDo.app.todo.TodoForm.TodoDelete;
import com.example.MyBatisToDo.app.todo.TodoForm.TodoFinish;
import com.example.MyBatisToDo.domain.model.Todo;
import com.example.MyBatisToDo.domain.service.todo.TodoService;

@Controller
@RequestMapping("todo")
public class TodoController {
	@Inject 
	TodoService todoService;

	@Inject
	Mapper beanMapper;

	@ModelAttribute 
	public TodoForm setUpForm() {
		TodoForm form = new TodoForm();
		return form;
	}

	@GetMapping("list")
	public String list(Model model) {
		Collection<Todo> todos = todoService.findAll();
		model.addAttribute("todos", todos); 
		return "todo/list"; 
	}

	@PostMapping("create")
	public String create(
			@Validated({ Default.class, TodoCreate.class }) TodoForm todoForm,
			BindingResult bindingResult, Model model,RedirectAttributes attributes) {

		// 入力エラーがあった場合、一覧画面に戻る。
		// Todo全件取得を再度行う必要があるので、listメソッドを再実行する。
		if(bindingResult.hasErrors()) {
			return list(model);
		}

		Todo todo = beanMapper.map(todoForm, Todo.class);

		try {
			todoService.create(todo);
		} catch(BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(ResultMessages.success().add(
				ResultMessage.fromText("Created successfully!")));
		return "redirect:/todo/list";

	}

	@PostMapping("finish")
	public String finish(
			@Validated({ Default.class, TodoFinish.class }) TodoForm form,
			BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.finish(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		
		attributes.addFlashAttribute(ResultMessages.success().add(
				ResultMessage.fromText("Finished successfully!")));
		return "redirect:/todo/list";
	}
	
	@PostMapping("delete")
	public String delete(
			@Validated({ Default.class, TodoDelete.class }) TodoForm form,
			BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			return list(model);
		}
		
		try {
			todoService.delete(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}
		
		attributes.addFlashAttribute(ResultMessages.success().add(
                ResultMessage.fromText("Deleted successfully!")));
        return "redirect:/todo/list";
	}


}