package com.apply.Kirana.controller;

import com.apply.Kirana.Model.StoreConfig;
import com.apply.Kirana.RateLimiterService;
import com.apply.Kirana.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private RateLimiterService rateLimiterService;
    @GetMapping("/all")
    public ResponseEntity<Object> getAllTransaction(){
        List<StoreConfig> transactions = storeService.FindAllData();
        return ResponseEntity.ok(transactions);
    }
    @PostMapping("/record")
    public ResponseEntity<String> recordTransaction(@RequestBody StoreConfig storeConfig) {
        // Check if the request can proceed within the rate limit
        if (!rateLimiterService.tryConsume()) {
            return ResponseEntity.status(429).body("Too many requests - try again later.");
        }

        String response = storeService.recordTransaction(storeConfig);
        if (response.startsWith("Error")) {
            return ResponseEntity.status(500).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateTransaction(@PathVariable String userId, @RequestBody StoreConfig storeConfig) {
        String response = storeService.updateTransaction(userId, storeConfig);
        if (response.startsWith("Transaction not found")) {
            return ResponseEntity.status(404).body(response);  // Return 404 if the transaction is not found
        }
        return ResponseEntity.ok(response);  // Return 200 status for success
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable String userId) {
        String response = storeService.deleteTransaction(userId);
        if (response.startsWith("Transaction not found")) {
            return ResponseEntity.status(404).body(response);  // Return 404 if the transaction is not found
        }
        return ResponseEntity.ok(response);  // Return 200 status for successful deletion
    }
}
