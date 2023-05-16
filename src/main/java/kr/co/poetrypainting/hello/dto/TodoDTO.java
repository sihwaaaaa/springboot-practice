package kr.co.poetrypainting.hello.dto;

import kr.co.poetrypainting.hello.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
  private String id;
  private String title;
  private boolean done;

  public TodoDTO(final TodoEntity entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.done = entity.isDone();
  }

  public static TodoEntity toEntity(final TodoDTO dto) {
    return TodoEntity.builder().id(dto.getId()).title(dto.title).done(dto.isDone()).build();
    
  }
}
