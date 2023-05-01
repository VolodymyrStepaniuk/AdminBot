package com.telegramBot.telegramBot.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChangeNickNameRepository extends CrudRepository<ChangeNickName,Integer> {
    @Query(value = "select nh.nh_id,nh.nh_oldname,nh.nh_newname,nh.nh_date,nh.nh_admin from nickname_history nh JOIN users as u ON u.u_id = nh.nh_owner WHERE u.u_name LIKE ?",nativeQuery = true)
    List<ChangeNickName> findAllByNickName(String nickName);
}
