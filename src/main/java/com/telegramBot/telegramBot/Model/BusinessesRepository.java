package com.telegramBot.telegramBot.Model;

import org.springframework.data.repository.CrudRepository;

public interface BusinessesRepository extends CrudRepository<Business,Integer> {
    Business findById(int id);
}
