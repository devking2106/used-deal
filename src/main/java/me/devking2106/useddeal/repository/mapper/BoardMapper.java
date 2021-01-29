package me.devking2106.useddeal.repository.mapper;


import org.apache.ibatis.annotations.Mapper;

import me.devking2106.useddeal.entity.Board;

@Mapper
public interface BoardMapper {

	int save(Board saveBoard);

}
