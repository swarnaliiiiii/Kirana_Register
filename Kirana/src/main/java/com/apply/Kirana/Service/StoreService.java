package com.apply.Kirana.Service;

import com.apply.Kirana.Model.StoreConfig;
import com.apply.Kirana.Repo.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepo storeRepo;
    @Autowired
    private CurrencyService currencyService;
    @CacheEvict(value = {"weeklyReport", "monthlyReport", "yearlyReport"}, allEntries = true)
    public String recordTransaction(StoreConfig storeConfig) {
        try {
            if (!storeConfig.getCurrency().equals("USD")) {
                double rate = currencyService.getExchangeRate(storeConfig.getCurrency());
                double convertedAmount = storeConfig.getAmount() * rate; // Convert to base currency (USD)
                storeConfig.setAmount(convertedAmount);
            }

            storeRepo.save(storeConfig);
            return "Transaction recorded successfully.";
        } catch (Exception e) {
            return "Error recording transaction: " + e.getMessage();
        }
    }

    // Fetch all transactions
    public List<StoreConfig> FindAllData() {
        return storeRepo.findAll();
    }
    @CacheEvict(value = {"weeklyReport", "monthlyReport", "yearlyReport"}, allEntries = true)
    public String updateTransaction(String userId, StoreConfig updatedTransaction) {
        Optional<StoreConfig> existingTransactionOpt = storeRepo.findById(userId);

        if (existingTransactionOpt.isPresent()) {
            StoreConfig existingTransaction = existingTransactionOpt.get();

            existingTransaction.setUserID(updatedTransaction.getUserID());
            existingTransaction.setAmount(updatedTransaction.getAmount());
            existingTransaction.setCurrency(updatedTransaction.getCurrency());
            existingTransaction.setType(updatedTransaction.getType());
            existingTransaction.setDate(updatedTransaction.getDate());

            storeRepo.save(existingTransaction);
            return "Transaction updated successfully.";
        } else {
            return "Transaction not found with userId: " + userId;
        }
    }

    // Delete a transaction by userId
    public String deleteTransaction(String userId) {
        Optional<StoreConfig> dataConfig = storeRepo.findById(userId);
        if (dataConfig.isPresent()) {
            storeRepo.deleteById(userId);
            return "Transaction with userId: " + userId + " deleted successfully.";
        } else {
            return "Transaction not found with userId: " + userId;
        }
    }
}
