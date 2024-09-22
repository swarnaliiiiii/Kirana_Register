package com.apply.Kirana.Repo;

import com.apply.Kirana.Model.StoreConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface StoreRepo extends MongoRepository<StoreConfig,String> {

    List<StoreConfig> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
