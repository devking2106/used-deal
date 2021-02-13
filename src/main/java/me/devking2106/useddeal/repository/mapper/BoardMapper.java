package me.devking2106.useddeal.repository.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.devking2106.useddeal.controller.request.BoardFindRequest;
import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.dto.BoardModifyDto;
import me.devking2106.useddeal.entity.Board;

@Mapper
public interface BoardMapper {

	int save(Board saveBoard);

	BoardDetailDto findById(Long id);

	List<BoardFindDto> findByUser(@Param("userId") Long userId, @Param("userIdResult") Long userIdResult);

	List<BoardFindDto> findAll(@Param("boardFindRequest") BoardFindRequest boardFindRequest,
		@Param("latitude") double latitude, @Param("longitude") double longitude);

	int updateByPull(@Param("id") Long id, @Param("userId") Long userId, @Param("saveTime") LocalDateTime saveTime);

	Long findByTimeStampDiff(@Param("id") Long id, @Param("userId") Long userId);

	int updateByStatus(@Param("id") Long id, @Param("userId") Long userId, @Param("status") Board.Status status,
		@Param("saveTime") LocalDateTime saveTime);

	int updateByBoard(@Param("id") Long id, @Param("boardModifyDto") BoardModifyDto boardModifyDto,
		@Param("updateTime") LocalDateTime updateTime);

	int deleteByBoard(Long id);
}
