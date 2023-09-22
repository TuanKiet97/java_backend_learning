package com.project.shopapp.controller;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.shopapp.dtos.CategoriesDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder(
	    @Valid @RequestBody OrderDTO orderDTO,
	    BindingResult result
    ) {
	try {
	    if(result.hasErrors()) {
		List<String> errorMessages = result.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.toList();
		return ResponseEntity.badRequest().body(errorMessages);
	    }
	    return ResponseEntity.ok("Order successfully");
	}  catch (Exception e) {
	    return ResponseEntity.badRequest().body(e.getMessage());
	}
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?>  getOrder(@Valid @PathVariable("user_id") Long userId) {
	try {
	    return ResponseEntity.ok("Lay ra danh sach user tu user id");
	} catch (Exception e) {
	    return ResponseEntity.badRequest().body(e.getMessage());
	}


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
	    @Valid @PathVariable long id,
	    @Valid @RequestBody OrderDTO orderDTO) {
	return ResponseEntity.ok("Cập nhật thông tin 1 order");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
	    @Valid @PathVariable Long id) {
	return ResponseEntity.ok("Delete id cho user thannh cong");
    }
}
