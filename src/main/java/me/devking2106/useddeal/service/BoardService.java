package me.devking2106.useddeal.service;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.repository.mapper.BoardMapper;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;

	public Board saveBoard(BoardSaveDto boardSaveDto) {
		// userId = 유저 id, locationName = 유저의 동네 , locationId = 동네 id, latitude = 위도, longitude = 경도
		long userId = 1;
		// 유저가 있는지 체크 userIsEmpty(userInfo);
		String locationName = "서울 종로구 청운동".trim();
		Long locationId = 1111010100L;
		Double latitude = 37.587111;
		Double longitude = 126.969069;

		// locationName 이 실제로 동네가 있는지 체크 후 있으면 등록 없으면 등록 안함
		Board boardInfo = boardSaveDto.toEntity(userId, locationName, locationId, latitude, longitude);
		// 유저의 동네와 저장하려는 동네가 일치하면 저장, 그렇지 않으면 저장 X
		if (locationName.equals(boardInfo.getLocationName())) {
			int saveResult = boardMapper.save(boardInfo);
			if (saveResult != 1) {
				throw new RuntimeException("게시글 저장에 실패 했습니다");
			}
		} else {
			String boardLocationName = boardInfo.getLocationName();
			throw new RuntimeException("동네가 일치하지 않습니다. 글쓰기를 하려면 " + boardLocationName + " 동네인증이 필요합니다 ");
		}
		//  boardInfo.getId() 받아와서 image 저장
		return boardInfo;
	}

	public static void boardIsEmpty(Object boards) {
		if (ObjectUtils.isEmpty(boards)) {
			throw new RuntimeException("게시글 검색 결과가 없습니다");
		}
	}


	@Transactional(readOnly = true)
	public List<Board> findAll() {
		List<Board> boards = boardMapper.findAll();
		return boards;
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findByLocationName(String location, int range) {
		// 임시 range 범위 0~15 처리 추후 Location.Range 로 처리
		if (0 > range || range > 15) {
			throw new RuntimeException("게시글 지역 검색 범위가 벗어났습니다. 0~15 사이의 값을 입력해주세요");
		}
		// location 지역이 있는지 확인 후 없는 지역이면 예외 처리

		// location = 서울 종로구 청운동, 위경도 값
		double latitude = 37.587111;
		double longitude = 126.969069;
		List<BoardFindDto> boards = boardMapper.findByLocation(location, latitude, longitude, range);
		return boards;
	}

	@Transactional(readOnly = true)
	public BoardDetailDto findById(Long boardId) {
		if (boardId < 1) {
			throw new RuntimeException("게시글 번호는 0보다 커야합니다");
		}
		BoardDetailDto boardInfo = boardMapper.findById(boardId);
		return boardInfo;
	}

	@Transactional(readOnly = true)
	public List<Board> findByTitleLike(String title) {
		List<Board> boards = boardMapper.findByTitleLike(title);
		return boards;
	}

	@Transactional(readOnly = true)
	public List<Board> findByContentLike(String content) {
		List<Board> boards = boardMapper.findByContentLike(content);
		return boards;
	}

	@Transactional(readOnly = true)
	public List<Board> findByTitleAndContentLike(String title, String content) {
		List<Board> boards = boardMapper.findByTitleAndContentLike(title, content);
		return boards;
	}

	@Transactional(readOnly = true)
	public List<Board> findAll(String title, String content) {
		List<Board> boards;
		if ((title == null || title.isBlank()) && (content == null || content.isBlank())) {
			boards = findAll();
		} else if ((title != null) && (content == null || content.isBlank())) {
			boards = findByTitleLike(title.trim());
		} else if (title == null || title.isBlank()) {
			boards = findByContentLike(content.trim());
		} else {
			boards = findByTitleAndContentLike(title.trim(), content.trim());
		}
		return boards;
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findByUser(Long userId) {
		if (userId < 1) {
			throw new RuntimeException("유저 번호는 0보다 커야합니다");
		}
		// userId 를 조회 후 있으면 조회하고 없으면 예외를 던져준다

		List<BoardFindDto> boards = boardMapper.findByUser(userId);
		return boards;
	}
}
