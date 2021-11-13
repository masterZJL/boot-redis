package com.zjl.study.bootredis.mapper;

import com.zjl.study.bootredis.entity.City;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CityMapper {

    @Select("select * from city where id = #{id}")
    @ResultType(City.class)
    City findById(@Param("id") int id);

    @Update("update city set name = #{city.name}, country = #{city.country} where id = #{city.id}")
    void updateCity(@Param("city") City city);

}
