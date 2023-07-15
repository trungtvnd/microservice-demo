package com.example.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hehehe")
public class InventoryJenkinsTestController {
    @GetMapping("get")
    ResponseEntity<String> get (){
        String d =  "ăfawfawrfawf";
        return ResponseEntity.ok(d);
    }

}
