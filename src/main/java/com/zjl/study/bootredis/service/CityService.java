package com.zjl.study.bootredis.service;

import com.zjl.study.bootredis.annotation.AlongCache;
import com.zjl.study.bootredis.annotation.AlongClearCache;
import com.zjl.study.bootredis.entity.City;
import com.zjl.study.bootredis.mapper.CityMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CityService {

    @Resource
    private CityMapper cityMapper;

//    @Cacheable(key = "#id", value = "city")
    @AlongCache(key = "#id", value = "city")
    public City getCityById(int id) {
        System.out.println("查询数据库了！！！");
        return cityMapper.findById(id);
    }

//    @CacheEvict(key = "#city.id", value = "city")
    @AlongClearCache(key = "#city.id", value = "city")
    public void updateCity(City city) {
        cityMapper.updateCity(city);
    }

}
