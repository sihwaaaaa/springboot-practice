package kr.co.poetrypainting.hello.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.poetrypainting.hello.model.TodoEntity;
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
  List<TodoEntity> findByUserId(String userId);

}
