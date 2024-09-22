package com.apply.Kirana.controller;

import com.apply.Kirana.Model.FinancialReport;
import com.apply.Kirana.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/weekly")
    public ResponseEntity<FinancialReport> getWeeklyReport() {
        FinancialReport report = reportService.getWeeklyReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly")
    public ResponseEntity<FinancialReport> getMonthlyReport() {
        FinancialReport report = reportService.getMonthlyReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/yearly")
    public ResponseEntity<FinancialReport> getYearlyReport() {
        FinancialReport report = reportService.getYearlyReport();
        return ResponseEntity.ok(report);
    }
}
