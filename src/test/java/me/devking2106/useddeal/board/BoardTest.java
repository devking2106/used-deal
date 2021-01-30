package me.devking2106.useddeal.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;

@SpringBootTest
@AutoConfigureMockMvc
class BoardTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	private Board board;

	final LocalDateTime saveTime = LocalDateTime.now();

	@BeforeEach
	void initBoard() {
		board = Board.builder()
			.id(1L)
			.userId(1L)
			.locationId(1111010100L)
			.locationName("서울 종로구 청운동")
			.title("title Test User1")
			.content("content Test User1")
			.price(1_000_000L)
			.categoryId(1L)
			.status(Board.Status.SALE.getStatus())
			.regDate(saveTime)
			.modDate(saveTime)
			.boardDate(saveTime)
			.isPriceSuggest(false)
			.isPull(true)
			.latitude(37.587111)
			.longitude(126.969069)
			.build();
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 유저의 위치와 게시글 작성 동네가 같을때 - 성공")
	void createSuccessBoard() throws Exception {
		// 유저의 위치는 서울 종로구 청운동
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.title("제목 청운동")
			.content("내용 청운동")
			.price(1_000_000L)
			.locationName("서울 종로구 청운동")
			.isPriceSuggest(true)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isCreated());
	}


	/**
	 * 게시글 생성 테스트 - 유저의 위치와 게시글 작성 동네가 다를때 - 실패
	 * 추가
	 */

	/**
	 * 게시글 생성 테스트 - 동네 이름이 없는 데이터 일때 - 실패
	 * 추가
	 */

	@Test
	@DisplayName("게시글 생성 테스트 - 제목, 내용 미입력 - 실패")
	void createFailureBoardNoTitleNoContent() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.price(1_000_000L)
			.locationName("서울 종로구 청운동")
			.isPriceSuggest(true)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 제목 미입력 - 실패")
	void createFailureBoardNoTitle() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.content("내용 청운동")
			.price(1_000_000L)
			.locationName("서울 종로구 청운동")
			.isPriceSuggest(true)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 내용 미입력 - 실패")
	void createFailureBoardNoContent() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.title("제목 청운동")
			.content("  ")
			.price(1_000_000L)
			.locationName("서울 종로구 청운동")
			.isPriceSuggest(true)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 가격 미입력 - 성공")
	void createSuccessBoardNoPrice() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.title("제목 청운동")
			.content("내용 청운동")
			.locationName("서울 종로구 청운동")
			.isPriceSuggest(true)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 가격 포맷이 다를 때 - 실패")
	void createFailureBoardPriceNotFormat() throws Exception {
		HashMap<String, String> boardMap = new HashMap<>();
		boardMap.put("title", "제목 청운동");
		boardMap.put("content", "내용 청운동");
		boardMap.put("price", "a");
		boardMap.put("locationName", "서울 종로구 청운동");
		boardMap.put("isPriceSuggest", "true");
		boardMap.put("categoryId", "1");
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardMap)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 카테고리 포맷 다를 때 - 실패")
	void createFailureBoardCategoryIdNotFormat() throws Exception {
		HashMap<String, String> boardMap = new HashMap<>();
		boardMap.put("title", "제목 청운동");
		boardMap.put("content", "내용 청운동");
		boardMap.put("price", "20000");
		boardMap.put("locationName", "서울 종로구 청운동");
		boardMap.put("isPriceSuggest", "true");
		boardMap.put("categoryId", "1a");
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardMap)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 가격 제안 여부 미입력 - 성공")
	void createSuccessBoardNoIsPriceSuggest() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.title("제목 청운동")
			.content("내용 청운동")
			.price(1_000_000L)
			.locationName("서울 종로구 청운동")
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 가격 제안 여부 입력 - 성공")
	void createSuccessBoardIsPriceSuggestNumber() throws Exception {
		HashMap<String, String> boardMap = new HashMap<>();
		boardMap.put("title", "제목 청운동");
		boardMap.put("content", "내용 청운동");
		boardMap.put("price", "20000");
		boardMap.put("locationName", "서울 종로구 청운동");
		boardMap.put("isPriceSuggest", "true");
		boardMap.put("categoryId", "1");
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardMap)))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 성공")
	void findSuccessBoardByAll() throws Exception {
		mockMvc.perform(get("/api/boards/all"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 제목 입력 - 성공")
	void findSuccessBoardByTitleAll() throws Exception {
		mockMvc.perform(get("/api/boards/all")
			.param("title", "테스트"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 내용 입력 - 성공")
	void findSuccessBoardByContentAll() throws Exception {
		mockMvc.perform(get("/api/boards/all")
			.param("content", "테스트"))
			.andDo(print())
			.andExpect(status().isOk());
	}


	@Test
	@DisplayName("게시글 전체 조회 테스트 - 제목, 내용 입력 - 성공")
	void findSuccessBoardByTitleAndContentAll() throws Exception {
		mockMvc.perform(get("/api/boards/all")
			.param("title", "테스트")
			.param("content", "테스트"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 제목, 내용 공백 입력 - findByAll 로 조회- 성공")
	void findSuccessBoardByNoTitleAndNoContentAll() throws Exception {
		mockMvc.perform(get("/api/boards/all")
			.queryParam("title", "  ")
			.queryParam("content", "  "))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 번호 조회 테스트 - 성공")
	void findSuccessByBoardId() throws Exception {
		mockMvc.perform(get("/api/boards/{id}", 1))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 번호 조회 테스트 - 데이터가 없을때 - 실패")
	void findFailureFindByBoardId() throws Exception {
		mockMvc.perform(get("/api/boards/{id}", 200))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 번호 조회 테스트 - 1보다 작은 값 입력 - 실패")
	void findFailureByBoardIdNotBoundsNumber() throws Exception {
		mockMvc.perform(get("/api/boards/{id}", -1))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 번호 조회 테스트 - 숫자가 아닌 값 입력 - 실패")
	void findFailureByBoardIdNumber() throws Exception {
		mockMvc.perform(get("/api/boards/{id}", "a"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역 입력 - 성공")
	void findSuccessByLocationName() throws Exception {
		mockMvc.perform(get("/api/boards")
			.queryParam("location", "서울 종로구 청운동"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * 게시글 지역이름으로 조회 - 지역 입력 - 없는 지역 - 실패
	 * 경우도 추가
	 */

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역, 범위 입력 - 성공")
	void findSuccessByLocationNameAndRange() throws Exception {
		mockMvc.perform(get("/api/boards")
			.queryParam("location", "서울 종로구 청운동")
			.queryParam("range", "15"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역, 범위 미입력 - 실패")
	void findFailureByLocationNameAndRange() throws Exception {
		mockMvc.perform(get("/api/boards"))
			.andDo(print())
			.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역 입력, 다른 범위 입력 - 실패")
	void findFailureByLocationNameAndRangeNotBounds() throws Exception {
		mockMvc.perform(get("/api/boards")
			.queryParam("location", "서울 종로구 청운동")
			.queryParam("range", "ONE"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

}
