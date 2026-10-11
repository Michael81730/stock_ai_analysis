package com.mc.stock_ai_analysis.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// GET http://127.0.0.1:8080/getStockData
// GET http://127.0.0.1:8080/getStockAiAnalysis

@RestController
public class StockAnalysisController {

    @GetMapping("/getStockData")
    public String getStockData() {
        return "Stock data retrieved successfully!";
    }

    @GetMapping("/getStockAiAnalysis")
    public String getStockAiAnalysis() {
        return "Stock AI analysis retrieved successfully!";
    }
}