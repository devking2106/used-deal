package me.devking2106.useddeal.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.controller.request.BoardFindRequest;
import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.dto.BoardModifyDto;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.error.exception.board.BoardDeleteFailedException;
import me.devking2106.useddeal.error.exception.board.BoardNotFoundException;
import me.devking2106.useddeal.error.exception.board.BoardNotMatchUserIdException;
import me.devking2106.useddeal.error.exception.board.BoardPullFailedException;
import me.devking2106.useddeal.error.exception.board.BoardSaveFailedException;
import me.devking2106.useddeal.error.exception.board.BoardStatusFailedException;
import me.devking2106.useddeal.error.exception.board.BoardStatusHideException;
import me.devking2106.useddeal.error.exception.board.BoardTimeStampException;
import me.devking2106.useddeal.error.exception.board.BoardUpdateFailedException;
import me.devking2106.useddeal.error.exception.location.TownNotMatchException;
import me.devking2106.useddeal.repository.mapper.BoardMapper;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;

	public Board register(BoardSaveDto boardSaveDto) {
		// userId = 유저 id, locationName = 유저의 동네 , locationId = 동네 id, latitude = 위도, longitude = 경도
		long userId = 1;
		// 유저가 있는지 체크 userIsEmpty(userInfo);
		String locationName = "서울 종로구 청운동".trim();
		Long locationId = 1111010100L;
		double latitude = 37.587111;
		double longitude = 126.969069;

		// locationName 이 실제로 동네가 있는지 체크 후 있으면 등록 없으면 등록 안함
		Board boardInfo = boardSaveDto.toEntity(userId, locationName, locationId, latitude, longitude);
		// 유저의 동네와 저장하려는 동네가 일치하면 저장, 그렇지 않으면 저장 X
		if (locationName.equals(boardInfo.getLocationName())) {
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

	public static void boardIsEmpty(Object boards) {
		if (ObjectUtils.isEmpty(boards)) {
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
	public List<BoardFindDto> findByUser(Long userId) {
		// userId 를 조회 후 있으면 조회하고 없으면 예외를 던져준다

		// 내 userId를 가져온다
		long userIdResult = 1;

		// 내 정보면 숨김을 표시해주고 내 정보가 아니면 숨김 게시글을 제외한다
		return boardMapper.findByUser(userId, userIdResult);
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findAll(BoardFindRequest boardFindRequest) {
		// 임시 boardFindRequest.range 범위 0~15 처리 추후 Location.Range 로 처리
		// location 지역이 있는지 확인 후 없는 지역이면 예외 처리

		// location = 서울 종로구 청운동, 위경도 값
		double latitude = 37.587111;
		double longitude = 126.969069;
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

	public void updateStatus(Long id, Board.Status status) {
		BoardDetailDto boardDetailDto = boardMapper.findById(id);
		boardIsEmpty(boardDetailDto);
		long userId = 1;
		if (userId != boardDetailDto.getUserId()) {
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

	public void updateBoard(Long id, BoardModifyDto boardModifyDto) {
		BoardDetailDto boardDetailDto = boardMapper.findById(id);
		boardIsEmpty(boardDetailDto);
		long userId = 1;
		if (userId != boardDetailDto.getUserId()) {
			throw new BoardNotMatchUserIdException();
		}
		LocalDateTime updateTime = LocalDateTime.now();
		int updateCount = boardMapper.updateBoard(id, boardModifyDto, updateTime);
		if (updateCount < 1) {
			throw new BoardUpdateFailedException();
		}
	}

	public void deleteById(Long id) {
		BoardDetailDto boardDetailDto = boardMapper.findById(id);
		boardIsEmpty(boardDetailDto);
		long userId = 1;
		if (userId != boardDetailDto.getUserId()) {
			throw new BoardNotMatchUserIdException();
		}
		int deleteCount = boardMapper.deleteById(id);
		if (deleteCount < 1) {
			throw new BoardDeleteFailedException();
		}
	}
}
