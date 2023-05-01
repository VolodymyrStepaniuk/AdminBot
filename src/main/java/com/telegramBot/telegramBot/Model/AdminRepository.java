package com.telegramBot.telegramBot.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin,Integer> {
    Admin findAdminByNickName(String nickName);
    @Query(value = "select u.u_a_level from users_admins u where u.u_a_name = ? and u.u_a_password = ?",nativeQuery = true)
    Byte findAdminLevelByNickNameAndPassword(String nickName,String password);
}
