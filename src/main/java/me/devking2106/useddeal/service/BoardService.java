package me.devking2106.useddeal.service;

import static me.devking2106.useddeal.common.utils.CustomUtil.*;
import static me.devking2106.useddeal.service.UserService.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devking2106.useddeal.controller.request.BoardFindRequest;
import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.dto.BoardModifyDto;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.entity.Location;
import me.devking2106.useddeal.entity.User;
import me.devking2106.useddeal.error.exception.board.BoardDeleteFailedException;
import me.devking2106.useddeal.error.exception.board.BoardNotMatchUserIdException;
import me.devking2106.useddeal.error.exception.board.BoardPullFailedException;
import me.devking2106.useddeal.error.exception.board.BoardSaveFailedException;
import me.devking2106.useddeal.error.exception.board.BoardStatusFailedException;
import me.devking2106.useddeal.error.exception.board.BoardStatusHideException;
import me.devking2106.useddeal.error.exception.board.BoardTimeStampException;
import me.devking2106.useddeal.error.exception.board.BoardUpdateFailedException;
import me.devking2106.useddeal.repository.mapper.BoardMapper;
import me.devking2106.useddeal.repository.mapper.LocationMapper;
import me.devking2106.useddeal.repository.mapper.UserMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;
	private final LocationMapper locationMapper;
	private final UserMapper userMapper;

	public void register(BoardSaveDto boardSaveDto, Long userId) {
		User userInfo = userMapper.findById(userId);
		userIsEmpty(userInfo);
		Board boardInfo = boardSaveDto.toEntity(userId, userInfo);
		int result = boardMapper.save(boardInfo);
		if (isNotApplication(result)) {
			throw new BoardSaveFailedException();
		}
	}

	@Transactional(readOnly = true)
	public BoardDetailDto findById(Long boardId) {
		// 내 userId 를 가져온다
		long userId = 1;
		BoardDetailDto boardInfo = boardMapper.findById(boardId);
		boardIsEmpty(boardInfo);
		// 글이 숨김이고 내가 작성한 글이 아닐 경우 보지 못한다
		if (boardInfo.isBoardNotHideAndMyBoard(userId)) {
			throw new BoardStatusHideException();
		}
		return boardInfo;
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findByUser(Long userId, Long sessionUserId) {
		User userInfo = userMapper.findById(userId);
		userIsEmpty(userInfo);
		return boardMapper.findByUser(userId, sessionUserId);
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findAll(BoardFindRequest boardFindRequest) {
		String locationName = boardFindRequest.getLocation();
		Location locationInfo = locationMapper.findByLocationName(locationName);
		locationIsEmpty(locationInfo);
		double latitude = locationInfo.getLatitude();
		double longitude = locationInfo.getLongitude();
		return boardMapper.findAll(boardFindRequest, latitude, longitude);
	}

	public void updatePull(Long id, Board.Status status, Long userId,
		BoardDetailDto boardDetailDto, LocalDateTime updateTime) {
		LocalDateTime boardDate = boardDetailDto.getBoardDate();
		long boardDateSeconds = Duration.between(boardDate, updateTime).getSeconds();
		long twoDaysSeconds = Duration.ofDays(2).getSeconds();
		if (boardDateSeconds < twoDaysSeconds) {
			throw new BoardTimeStampException(String.valueOf(boardDateSeconds));
		}
		int updateCount = boardMapper.updateStatus(id, userId, status, updateTime);
		if (updateCount < 1) {
			throw new BoardPullFailedException();
		}
	}

	public void updateStatus(Long id, Board.Status status, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(id);
		boardIsEmpty(boardDetailDto);
		if (!userId.equals(boardDetailDto.getUserId())) {
			throw new BoardNotMatchUserIdException();
		}
		if (!boardDetailDto.isStatusUpdatable(status)) {
			return;
		}
		LocalDateTime updateTime = LocalDateTime.now();
		if (status == Board.Status.PULL) {
			updatePull(id, status, userId, boardDetailDto, updateTime);
		} else {
			updateStatus(id, status, userId, updateTime);
		}
	}

	private void updateStatus(Long id, Board.Status status, long userId, LocalDateTime updateTime) {
		int updateCount = boardMapper.updateStatus(id, userId, status, updateTime);
		if (updateCount < 1) {
			throw new BoardStatusFailedException(status);
		}
	}

	public void updateBoard(Long id, BoardModifyDto boardModifyDto, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(id);
		boardIsEmpty(boardDetailDto);
		if (!userId.equals(boardDetailDto.getUserId())) {
			throw new BoardNotMatchUserIdException();
		}
		LocalDateTime updateTime = LocalDateTime.now();
		int updateCount = boardMapper.updateBoard(id, boardModifyDto, updateTime);
		if (updateCount < 1) {
			throw new BoardUpdateFailedException();
		}
	}

	public void deleteById(Long id, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(id);
		boardIsEmpty(boardDetailDto);
		if (!userId.equals(boardDetailDto.getUserId())) {
			throw new BoardNotMatchUserIdException();
		}
		int deleteCount = boardMapper.deleteById(id);
		if (deleteCount < 1) {
			throw new BoardDeleteFailedException();
		}
	}

	public boolean isNotApplication(int result) {
		return result < 1;
	}
}
