package com.apply.Kirana.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor

public class FinancialReport {
    private final double totalCredit;
    private final double totalDebit;
    private final double netFlow;
    private final LocalDate startDate;
    private final LocalDate endDate;

}
