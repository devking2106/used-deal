package me.devking2106.useddeal.board;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.devking2106.useddeal.common.utils.type.CommonStatus;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.repository.mapper.BoardMapper;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
	@Mock
	private BoardMapper boardMapper;

	private Board board;

	@BeforeEach
	void initBoard() {
		//given
		final LocalDateTime saveTime = LocalDateTime.now();
		board = Board.builder()
			.userId(1L)
			.locationId(1111010100L)
			.locationName("서울 종로구 청운동")
			.title("제목 청운동")
			.content("내용 청운동")
			.price(1_000_000L)
			.categoryId(1L)
			.status(Board.Status.SALE)
			.regDate(saveTime)
			.modDate(saveTime)
			.boardDate(saveTime)
			.priceSuggestYN(CommonStatus.N)
			.pullYN(CommonStatus.Y)
			.viewCount(0L)
			.latitude(37.587111)
			.longitude(126.969069)
			.build();
	}

	@Test
	@DisplayName("게시글 생성 테스트")
	void saveBoard() {
		//when
		when(boardMapper.save(board)).thenReturn(1);
		int saveCount = boardMapper.save(board);
		//then
		assertEquals(saveCount, 1);
	}

}
