package me.devking2106.useddeal.service;

import static me.devking2106.useddeal.service.LocationService.*;
import static me.devking2106.useddeal.service.UserService.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
import me.devking2106.useddeal.error.exception.board.BoardNotFoundException;
import me.devking2106.useddeal.error.exception.board.BoardNotMatchUserIdException;
import me.devking2106.useddeal.error.exception.board.BoardPullFailedException;
import me.devking2106.useddeal.error.exception.board.BoardSaveFailedException;
import me.devking2106.useddeal.error.exception.board.BoardStatusFailedException;
import me.devking2106.useddeal.error.exception.board.BoardStatusHideException;
import me.devking2106.useddeal.error.exception.board.BoardDateLessThanTwoDaysException;
import me.devking2106.useddeal.error.exception.board.BoardUpdateFailedException;
import me.devking2106.useddeal.error.exception.location.TownNotMatchException;
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

	public Board register(BoardSaveDto boardSaveDto, Long userId) {
		// userId = 유저 id, locationName = 유저의 동네 , locationId = 동네 id, latitude = 위도, longitude = 경도
		User userInfo = userMapper.findById(userId);
		userIsEmpty(userInfo);
		String locationName = boardSaveDto.getLocationName();
		String userLocationName = userInfo.getLocationName();
		// locationName 이 실제로 동네가 있는지 체크 후 있으면 등록 없으면 등록 안함
		Location locationInfo = locationMapper.findByLocationName(locationName);
		locationIsEmpty(locationInfo);
		Board boardInfo = boardSaveDto.toEntity(userId, locationInfo);
		// 유저의 동네와 저장하려는 동네가 일치하면 저장, 그렇지 않으면 저장 X
		if (userLocationName.equals(boardInfo.getLocationName())) {
			int saveResult = boardMapper.save(boardInfo);
			if (saveResult != 1) {
				throw new BoardSaveFailedException();
			}
		} else {
			String boardLocationName = boardInfo.getLocationName();
			throw new TownNotMatchException(boardLocationName);
		}
		//  boardInfo.getId() 받아와서 image 저장
		return boardInfo;
	}

	public static void boardIsEmpty(BoardDetailDto board) {
		if (ObjectUtils.isEmpty(board)) {
			throw new BoardNotFoundException();
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

	public void updatePull(Long boardId, Board.Status status, Long userId,
		BoardDetailDto boardDetailDto, LocalDateTime updateTime) {
		LocalDateTime boardDate = boardDetailDto.getBoardDate();
		long boardDateSeconds = Duration.between(boardDate, updateTime).getSeconds();
		long twoDaysSeconds = Duration.ofDays(2).getSeconds();
		if (isLessThanTwoDays(boardDateSeconds, twoDaysSeconds)) {
			throw new BoardDateLessThanTwoDaysException(String.valueOf(boardDateSeconds));
		}
		int result = boardMapper.updateStatus(boardId, userId, status, updateTime);
		if (isNotApplication(result)) {
			log.info("게시글 끌어올리기 실패 : user id = {} , board id = {}", userId, boardId);
			throw new BoardPullFailedException();
		}
	}

	public void updateStatus(Long boardId, Board.Status status, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(boardId);
		boardIsEmpty(boardDetailDto);
		if (boardDetailDto.isOwnerTo(userId)) {
			throw new BoardNotMatchUserIdException();
		}
		if (!boardDetailDto.isStatusUpdatable(status)) {
			return;
		}
		LocalDateTime updateTime = LocalDateTime.now();
		if (status == Board.Status.PULL) {
			updatePull(boardId, status, userId, boardDetailDto, updateTime);
		} else {
			updateStatus(boardId, status, userId, updateTime);
		}
	}

	private void updateStatus(Long boardId, Board.Status status, Long userId, LocalDateTime updateTime) {
		int result = boardMapper.updateStatus(boardId, userId, status, updateTime);
		if (isNotApplication(result)) {
			log.info("게시글 상태 변경 실패 : user id = {} , board id = {}", userId, boardId);
			throw new BoardStatusFailedException(status);
		}
	}

	public void updateBoard(Long boardId, BoardModifyDto boardModifyDto, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(boardId);
		boardIsEmpty(boardDetailDto);
		if (boardDetailDto.isOwnerTo(userId)) {
			throw new BoardNotMatchUserIdException();
		}
		LocalDateTime updateTime = LocalDateTime.now();
		int result = boardMapper.updateBoard(boardId, boardModifyDto, updateTime);
		if (isNotApplication(result)) {
			log.info("게시글 수정 실패 : user id = {} , board id = {}", userId, boardId);
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

	private boolean isNotApplication(int result) {
		return result < 1;
	}

	private boolean isLessThanTwoDays(long boardDateSeconds, long twoDaysSeconds) {
		return boardDateSeconds < twoDaysSeconds;
	}
}
