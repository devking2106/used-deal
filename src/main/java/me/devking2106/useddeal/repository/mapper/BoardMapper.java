package me.devking2106.useddeal.repository.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.entity.Board;

@Mapper
public interface BoardMapper {

	int save(Board saveBoard);

	List<Board> findByAll();

	List<BoardFindDto> findByLocation(@Param("locationName") String locationName, @Param("latitude") Double latitude,
		@Param("longitude") Double longitude, @Param("range") int range);

	BoardDetailDto findById(Long id);

	List<Board> findByTitleLike(String title);

	List<Board> findByContentLike(String content);

	List<Board> findByTitleAndContentLike(@Param("title") String title, @Param("content") String content);
}
