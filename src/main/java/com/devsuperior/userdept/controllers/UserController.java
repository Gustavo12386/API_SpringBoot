package com.devsuperior.userdept.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.userdept.entities.Department;
import com.devsuperior.userdept.entities.User;
import com.devsuperior.userdept.repositories.DepartmentRepository;
import com.devsuperior.userdept.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
    
	@GetMapping
    public List<User> findAll(){
	  List<User> result = userRepository.findAll(); 
	  return result;
   }
	
	@GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id){
	  User result = userRepository.findById(id).get(); 
	  return result;
   }
	
	@PostMapping
    public User insert(@RequestBody User user){
	  User result = userRepository.save(user); 
	  return result;
   }	
   
	@PutMapping(value = "/{id}")
    public User updateById(@RequestBody User user, @PathVariable Long id){
	  User result = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));	  
	
	  result.setName(user.getName());
	  result.setEmail(user.getEmail());
	  
	  if (user.getDepartment() != null && user.getDepartment().getId() != null) {
          Department department = departmentRepository.findById(user.getDepartment().getId())
                  .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
          result.setDepartment(department);
      }
	  return userRepository.save(result);
   }	
	
	@DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
	   userRepository.deleteById(id);
	}
}
