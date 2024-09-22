package com.apply.Kirana.Service;

import com.apply.Kirana.Model.FinancialReport;
import com.apply.Kirana.Model.StoreConfig;
import com.apply.Kirana.Repo.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private StoreRepo storeRepo;
    @Cacheable(value = "weeklyReport", unless = "#result == null")
    public FinancialReport getWeeklyReport() {
        return generateReportForPeriod(LocalDate.now().minusWeeks(1), LocalDate.now());
    }
    @Cacheable(value = "monthlyReport", unless = "#result == null")
    public FinancialReport getMonthlyReport() {
        return generateReportForPeriod(LocalDate.now().minusMonths(1), LocalDate.now());
    }
    @Cacheable(value = "yearlyReport", unless = "#result == null")
    public FinancialReport getYearlyReport() {
        return generateReportForPeriod(LocalDate.now().minusYears(1), LocalDate.now());
    }

    private FinancialReport generateReportForPeriod(LocalDate startDate, LocalDate endDate) {
        List<StoreConfig> transactions = storeRepo.findByDateBetween(startDate, endDate);

        double totalCredit = 0;
        double totalDebit = 0;

        for (StoreConfig transaction : transactions) {
            if ("credit".equalsIgnoreCase(transaction.getType())) {
                totalCredit += transaction.getAmount();
            } else if ("debit".equalsIgnoreCase(transaction.getType())) {
                totalDebit += transaction.getAmount();
            }
        }

        double netFlow = totalCredit - totalDebit;
        return new FinancialReport(totalCredit, totalDebit, netFlow, startDate, endDate);
    }
}
