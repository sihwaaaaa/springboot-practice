package kr.co.poetrypainting.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.poetrypainting.hello.dto.ResponseDTO;
import kr.co.poetrypainting.hello.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")
public class TestController {
  @GetMapping
  public String testController(){
    return "Hello World";
  }
  @GetMapping("/testGetMapping")
  public String testControllerWithpath(){
    return "Hello World testGetMapping";
  }
  @GetMapping("/{id}")
  public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
    return "Hello World! ID" + id;
  }
  @GetMapping("/testRequestParam")
  public String testControllerRequestParam(@RequestParam(required = false) int id) {
    return "Hello World! ID" + id;
    
  }
  @GetMapping("/requestBody")
  public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
    return "Hello World! ID : " + testRequestBodyDTO.getId() + ", message : " + testRequestBodyDTO.getMessage();
  }
  @GetMapping("/requestDTO")
  public String testControllerDTO(TestRequestBodyDTO testRequestBodyDTO) {
    return "Hello World! ID : " + testRequestBodyDTO.getId() + ", message : " + testRequestBodyDTO.getMessage();
  }

  //반환테스트
  @GetMapping("/testResponseBody")
  public ResponseDTO<String> testControllerResponseBody() {
    List<String> list = new ArrayList<>();
    list.add("hello world i`m responseDTO");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return responseDTO;
  }
  @GetMapping("/testResponseEntity")
  public ResponseEntity<?> ResponseEntity() {
    List<String> list = new ArrayList<>();
    list.add("hello world i`m responseEntity. And you got 400!");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.ok().body(responseDTO);
  }
}
