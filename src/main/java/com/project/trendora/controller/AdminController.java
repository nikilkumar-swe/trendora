package com.project.trendora.controller;

import com.project.trendora.service.Impl.AdminServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceImpl adminServices;

    public AdminController(AdminServiceImpl adminServices){
        this.adminServices=adminServices;
    }

    @PutMapping("/orders/accept/{orderId}")
    public ResponseEntity<String> acceptOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(adminServices.acceptOrder(orderId));
    }
}
