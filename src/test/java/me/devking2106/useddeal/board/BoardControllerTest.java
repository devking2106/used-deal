package me.devking2106.useddeal.board;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.devking2106.useddeal.dto.BoardModifyDto;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.dto.BoardDate;
import me.devking2106.useddeal.dto.LongitudeAndLatitude;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.error.exception.board.BoardNotFoundException;
import me.devking2106.useddeal.error.exception.board.BoardTimeStampException;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class BoardControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void initBoard() {
		final LocalDateTime saveTime = LocalDateTime.now();
		BoardDate boardDate = BoardDate.builder()
			.boardDate(saveTime)
			.regDate(saveTime)
			.modDate(saveTime)
			.build();
		LongitudeAndLatitude longitudeAndLatitude = LongitudeAndLatitude.builder()
			.latitude(37.587111)
			.longitude(126.969069)
			.build();
		Board board = Board.builder()
			.id(1L)
			.userId(1L)
			.locationId(1111010100L)
			.locationName("서울 종로구 청운동")
			.title("title Test User1")
			.content("content Test User1")
			.price(1_000_000L)
			.categoryId(1L)
			.status(Board.Status.SALE)
			.boardDate(boardDate)
			.isPull(true)
			.longitudeAndLatitude(longitudeAndLatitude)
			.build();
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 제목, 내용 미입력 - 실패")
	void createFailureBoardNoTitleNoContent() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.price(1_000_000L)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					MethodArgumentNotValidException.class)));
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 제목 미입력 - 실패")
	void createFailureBoardNoTitle() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.content("내용 청운동")
			.price(1_000_000L)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					MethodArgumentNotValidException.class)));
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 내용 미입력 - 실패")
	void createFailureBoardNoContent() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.title("제목 청운동")
			.content("  ")
			.price(1_000_000L)
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					MethodArgumentNotValidException.class)));
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 가격 미입력 - 성공")
	void createSuccessBoardNoPrice() throws Exception {
		BoardSaveDto boardSaveDto = BoardSaveDto.builder()
			.title("제목 청운동")
			.content("내용 청운동")
			.categoryId(1L)
			.build();
		mockMvc.perform(post("/api/boards")
			.sessionAttr("ID", 1L)
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
		boardMap.put("categoryId", "1");
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardMap)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					HttpMessageNotReadableException.class)));
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 카테고리 포맷 다를 때 - 실패")
	void createFailureBoardCategoryIdNotFormat() throws Exception {
		HashMap<String, String> boardMap = new HashMap<>();
		boardMap.put("title", "제목 청운동");
		boardMap.put("content", "내용 청운동");
		boardMap.put("price", "20000");
		boardMap.put("categoryId", "1a");
		mockMvc.perform(post("/api/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardMap)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					HttpMessageNotReadableException.class)));
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 성공")
	void findSuccessBoardByAll() throws Exception {
		mockMvc.perform(get("/api/boards")
			.sessionAttr("ID", 1L)
			.queryParam("location", "서울 종로구 청운동"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 제목 입력 - 성공")
	void findSuccessBoardByTitleAll() throws Exception {
		mockMvc.perform(get("/api/boards")
			.sessionAttr("ID", 1L)
			.queryParam("location", "서울 종로구 청운동")
			.param("TITLE", "테스트"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 내용 입력 - 성공")
	void findSuccessBoardByContentAll() throws Exception {
		mockMvc.perform(get("/api/boards")
			.sessionAttr("ID", 1L)
			.queryParam("location", "서울 종로구 청운동")
			.param("CONTENT", "테스트"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트 - 제목 공백 입력 - findAll 로 조회- 성공")
	void findSuccessBoardByNoTitleAndNoContentAll() throws Exception {
		mockMvc.perform(get("/api/boards")
			.sessionAttr("ID", 1L)
			.queryParam("location", "서울 종로구 청운동")
			.queryParam("TITLE", "  "))
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
			.andExpect(status().isNotFound())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					BoardNotFoundException.class)));
	}

	@Test
	@DisplayName("게시글 번호 조회 테스트 - 1보다 작은 값 입력 - 실패")
	void findFailureByBoardIdNotBoundsNumber() throws Exception {
		mockMvc.perform(get("/api/boards/{id}", -1))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					BoardNotFoundException.class)));
	}

	@Test
	@DisplayName("게시글 번호 조회 테스트 - 숫자가 아닌 값 입력 - 실패")
	void findFailureByBoardIdNumber() throws Exception {
		mockMvc.perform(get("/api/boards/{id}", "a"))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					MethodArgumentTypeMismatchException.class)));
	}

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역 입력 - 성공")
	void findSuccessByLocationName() throws Exception {
		mockMvc.perform(get("/api/boards")
			.sessionAttr("ID", 1L)
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
			.sessionAttr("ID", 1L)
			.queryParam("location", "서울 종로구 청운동")
			.queryParam("range", "10"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역, 범위 미입력 - 성공")
	void findFailureByLocationNameAndRange() throws Exception {
		mockMvc.perform(get("/api/boards")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 지역이름으로 조회 - 지역 입력, 다른 범위 입력 - 실패")
	void findFailureByLocationNameAndRangeNotBounds() throws Exception {
		mockMvc.perform(get("/api/boards")
			.queryParam("location", "서울 종로구 청운동")
			.queryParam("range", "16"))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					BindException.class)));
	}

	/*====================================================수정===========================================*/

	@Order(0)
	@Test
	@DisplayName("게시글 상태 변경 - SALE_COMPLETED - 성공")
	void updateByStatusSaleCompleted() throws Exception {
		mockMvc.perform(patch("/api/boards/1/status")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString("SALE_COMPLETED")))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Order(10)
	@Test
	@DisplayName("게시글 상태 변경 - HIDE - 성공")
	void updateByStatusHide() throws Exception {
		mockMvc.perform(patch("/api/boards/1/status")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString("HIDE")))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Order(30)
	@Test
	@DisplayName("게시글 상태 변경 - PULL - 성공")
	void updateByStatusPull() throws Exception {
		mockMvc.perform(patch("/api/boards/2/status")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString("PULL")))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Order(40)
	@Test
	@DisplayName("게시글 상태 변경 - PULL (게시일 2일 이내)- 실패")
	void updateFailureByStatusPull() throws Exception {
		mockMvc.perform(patch("/api/boards/1/status")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString("PULL")))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					BoardTimeStampException.class)));
	}

	@Order(50)
	@Test
	@DisplayName("게시글 수정 - 성공")
	void updateBoard() throws Exception {
		BoardModifyDto boardModifyDto = BoardModifyDto.builder()
			.title("제목 청운동")
			.content("내용 청운동")
			.price(1_000_000L)
			.categoryId(1L)
			.build();
		mockMvc.perform(put("/api/boards/1")
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardModifyDto)))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	/*====================================================삭제===========================================*/

	@Order(60)
	@Test
	@DisplayName("게시글 삭제 - 성공")
	void deleteBoard() throws Exception {
		mockMvc.perform(delete("/api/boards/2")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

}
