package com.apply.Kirana.Model;

import org.springframework.data.annotation.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "JarCon")
public class StoreConfig {
   @Id
    private String userID;
    private double amount;
    private String currency;
    private String type;
    private Date date;
}
