package com.telegramBot.telegramBot.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoneyLogRepository extends CrudRepository<MoneyLog,Integer> {
    @Query(value = "select ml.log_id,ml.date,ml.money,ml.from_name,ml.name,ml.reason from money_logs ml where ml.name like ? and ml.date like ?",nativeQuery = true)
    List<MoneyLog> findAllByNickNameAndDateLike(String nickName,String date);
}
