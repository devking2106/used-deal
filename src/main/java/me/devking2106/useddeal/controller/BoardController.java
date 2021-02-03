package me.devking2106.useddeal.controller;




import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.controller.request.BoardRequest;
import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

	private final BoardService boardService;

	@PostMapping("/boards")
	public ResponseEntity<Board> register(@Valid @RequestBody BoardSaveDto boardSaveDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new RuntimeException("입력 범위를 확인해주세요");
		}
		Board boardInfo = boardService.saveBoard(boardSaveDto);
		return new ResponseEntity<>(boardInfo, HttpStatus.CREATED);
	}

	@GetMapping("/boards/{id}")
	public ResponseEntity<BoardDetailDto> findByBoardId(@PathVariable @Min(0) Long id) {
		BoardDetailDto boardInfo = boardService.findById(id);
		return new ResponseEntity<>(boardInfo, HttpStatus.OK);
	}

	@GetMapping("/boards")
	public ResponseEntity<List<BoardFindDto>> findAll(@Valid BoardRequest.Find boardFindRequest) {
		List<BoardFindDto> boards = boardService.findAll(boardFindRequest);
		return getBoardFindDtoListResponseEntity(boards);
	}

	@GetMapping("users/{userId}/boards")
	public ResponseEntity<List<BoardFindDto>> findByUser(@PathVariable Long userId) {
		List<BoardFindDto> boards = boardService.findByUser(userId);
		return getBoardFindDtoListResponseEntity(boards);
	}

	private ResponseEntity<List<BoardFindDto>> getBoardFindDtoListResponseEntity(List<BoardFindDto> boards) {
		if (CollectionUtils.isEmpty(boards)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}

}
