package com.telegramBot.telegramBot.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player,Integer> {
    Player findPlayerByNickName(String nickName);
    @Query(value = "SELECT f.f_name FROM fractions f JOIN users u on u.u_member = f.f_id WHERE u.u_name = ?",nativeQuery = true)
    String findOrgNameByNickName(String nickName);
}