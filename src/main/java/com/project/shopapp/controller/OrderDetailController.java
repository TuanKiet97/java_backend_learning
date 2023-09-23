package com.project.shopapp.controller;

import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetails(
	    @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
	    return ResponseEntity.ok("Order successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
	    @Valid @PathVariable("id") Long id) {
	return ResponseEntity.ok("Get Order detail with id"+id);
    }
    @GetMapping("/{id}/{orderId}")
    public ResponseEntity <?> getOrderDetails(
	    @Valid @PathVariable("orderId") Long orderId) {
	return ResponseEntity.ok("Get Order Details with" +orderId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
	    @Valid @PathVariable("id") Long id,
	    @RequestBody OrderDetailDTO newOrderDetailDTO) {
	return ResponseEntity.ok("Get Order detail with id"+id +"New Order Detail" + newOrderDetailDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(
	    @Valid @PathVariable("id") Long id) {
	return ResponseEntity.ok("Delete OK");
    }
}
