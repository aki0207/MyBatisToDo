package com.example.MyBatisToDo.domain.repository.todo;

import java.util.Collection;
import com.example.MyBatisToDo.domain.model.Todo;
import java.util.Optional;


public interface TodoRepository {
	// TODOの1件取得
    Optional<Todo> findById(String todoId);
    // TODOの全件取得
    Collection<Todo> findAll();
    // TODOの1件作成
    void create(Todo todo);
    // TODOの1件作成
    boolean updateById(Todo todo);
    // TODOの1件削除
    void deleteById(Todo todo);
    // 完了済みTODO件数の取得
    // long countByFinished(boolean)の引数としてtrueを渡すと「完了済みの件数」、 falseを渡すと「未完了の件数」が取得できる仕様としている。
    long countByFinished(boolean finished);
    

}
