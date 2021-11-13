package com.zjl.study.bootredis.controller;

import com.zjl.study.bootredis.entity.City;
import com.zjl.study.bootredis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CityService cityService;

    @PostMapping("/add")
    public String testAdd(City city) {
        redisTemplate.opsForValue().set(city.getName(), city);
        return "ok";
    }

    @PostMapping("/update")
    public String testUpdate(City city) {
        redisTemplate.opsForValue().set(city.getName(), city);
        return "ok";
    }

    @DeleteMapping("/delete")
    public String testDelete(String name) {
        redisTemplate.delete(name);
        return "ok";
    }

    @GetMapping("/get")
    public City testQuery(String name) {
        City city = (City) redisTemplate.opsForValue().get(name);
        return city;
    }

    @GetMapping("/city/{id}")
    public City getOne(@PathVariable("id") int id) {
        return cityService.getCityById(id);
    }

    @PutMapping("/city")
    public String updateCity(City city) {
        cityService.updateCity(city);
        return "ok";
    }

}
