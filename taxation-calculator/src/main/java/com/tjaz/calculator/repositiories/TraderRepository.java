package com.tjaz.calculator.repositiories;

import com.tjaz.calculator.entities.Trader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TraderRepository extends CrudRepository<Trader, Long> {

    // Fetch trader by traderId
    @Query("SELECT t FROM Trader t WHERE t.traderId = :traderId")
    Trader fetchTrader(String traderId);


}
