package kr.co.poetrypainting.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import kr.co.poetrypainting.hello.model.TodoEntity;
import kr.co.poetrypainting.hello.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService {
  @Autowired
  private TodoRepository repository;

  public String testService() {
    //TodoEntity 생성
    TodoEntity entity = TodoEntity.builder().title("my first todo item").build();
    //TodoEntity 저장
    repository.save(entity);
    //TodoEntity 검색
    TodoEntity savedeEntity = repository.findById(entity.getId()).get();
    return savedeEntity.getTitle();
  }
  public List<TodoEntity> create(final TodoEntity entity){
    validate(entity);
    repository.save(entity);
    log.info("entity id : {} is saved", entity.getId());
    
    return repository.findByUserId(entity.getUserId());
    
  }
  
  public List<TodoEntity> retrieve(final String userId) {
    return repository.findByUserId(userId);
  }
  private void validate(final TodoEntity entity){
    if (entity == null) {
      log.warn("Entity cannot be null");
      throw new RuntimeException("Entity cannot be null");
      
    }
    if (entity.getUserId() == null) {
      log.warn("Unknown user.");
      throw new RuntimeException("Unknown user.");
      
    }

  }
  public List<TodoEntity> update(final TodoEntity entity) {
    //저장할 엔티티가 유효한지 확인한다. 이메서드는 2.3.1 Create Todo에서 구현했다
    validate(entity);
    
    //넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문
    final Optional<TodoEntity> original = repository.findById(entity.getId());

    original.ifPresent(todo -> {
      //반환된 TOdoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
      todo.setTitle(entity.getTitle());
      todo.setDone(entity.isDone());
      //데이터베이스에 새 값을 저장한다.
      repository.save(todo);
    });

    //2.3.2 Retrieve Todo에서 만든 메서드를 이용해 유저의 모든 Todo 리스트를 리턴한다.
    return retrieve(entity.getUserId());
    
  }
  public List<TodoEntity> delete(final TodoEntity entity) {
    validate(entity);

    try {
      repository.delete(entity);
    } catch (Exception e) {
      // TODO: handle exception
      log.error("error deleting entity", entity.getId(),e);
      throw new RuntimeException("error deleting entity" + entity.getId());
    }
    return retrieve(entity.getUserId());
  }
}
