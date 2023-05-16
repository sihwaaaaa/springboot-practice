package kr.co.poetrypainting.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.poetrypainting.hello.dto.ResponseDTO;
import kr.co.poetrypainting.hello.dto.TodoDTO;
import kr.co.poetrypainting.hello.model.TodoEntity;
import kr.co.poetrypainting.hello.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
  @Autowired
  private TodoService service;

  //@GetMapping
  public ResponseEntity<?> testTodo() {
    List<String> list = new ArrayList<>();
    list.add(service.testService());
    return ResponseEntity.ok().body(ResponseDTO.<String>builder().data(list).build());
  }
  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
    try {
      String temporaryUserid =  "temporary-user";
      //TodoEntity로 변환한다.
      TodoEntity entity = TodoDTO.toEntity(dto);

      //id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다
      entity.setId(null);

      //임시 유저 아이디를 설정해준다. 이부분은 4장 인증과 인가에서 수정할 예정이다.
      //지금은 인증과 인가 기능이 없으므로 한 유저만 로그인 없이 사용 가능한 애플리케이션인 셈이다
      entity.setUserId(temporaryUserid);

      //서비스를 이용해 Todo엔티티를 생성한다.
      List<TodoEntity> entities = service.create(entity);

      //자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다.
      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      //변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다.
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

      //ResponseDTO를 리턴한다.
      return ResponseEntity.ok().body(response);

    } catch (Exception e) {
      // 혹시 예외가 나는 경우 dto대신 error에 메세지를 넣어 리턴한다.
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }
  }
  @GetMapping
  public ResponseEntity<?> retrieveTodoList() {
    String temporaryUserId = "temporary-user";
    
    List<TodoEntity> entities = service.retrieve(temporaryUserId);

    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
    
    
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
    
    return ResponseEntity.ok().body(response);
    
  }
  @PutMapping
  public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
   
    String temporaryUserId = "temporary-user";

    TodoEntity entity = TodoDTO.toEntity(dto);
    
    entity.setUserId(temporaryUserId);

    List<TodoEntity> entities = service.update(entity);
    
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
    
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    return ResponseEntity.ok().body(response);
    
  }
  @DeleteMapping
  public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
    try {
      String temporaryUserId = "temporary-user";
  
      TodoEntity entity = TodoDTO.toEntity(dto);
      
      entity.setUserId(temporaryUserId);
  
      List<TodoEntity> entities = service.delete(entity);
      
      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
      
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
      return ResponseEntity.ok().body(response);

    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }
   
    

    
  }

}