package me.devking2106.useddeal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.repository.mapper.BoardMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	private final BoardMapper boardMapper;

	public Board saveBoard(BoardSaveDto boardSaveDto) throws Exception {

		// userId = 유저 id, locationName = 유저의 동네 , locationId = 동네 id, latitude = 위도, longitude = 경도
		long userId = 1;
		String locationName = "서울 종로구 청운동".trim();
		Long locationId = 1111010100L;
		Double latitude = 37.587111;
		Double longitude= 126.969069;

		// locationName 이 실제로 동네가 있는지 체크 후 있으면 등록 없으면 등록 안함

		Board boardInfo = boardSaveDto.toEntity(userId, locationName, locationId, latitude, longitude);
		System.out.println("boardInfo = " + boardInfo);
		// 유저의 동네와 저장하려는 동네가 일치하면 저장, 그렇지 않으면 저장 X
		if (locationName.equals(boardInfo.getLocationName())) {
			int saveCount = boardMapper.save(boardInfo);
			if (saveCount != 1) {
				throw new Exception("게시글 저장에 실패 했습니다");
			}
		} else {
			String boardLocationName = boardInfo.getLocationName();
			throw new Exception("동네가 일치하지 않습니다. 글쓰기를 하려면 "+boardLocationName+" 동네인증이 필요합니다");
		}
		//  boardInfo.getId() 받아와서 image 저장

		return boardInfo;
	}

}
