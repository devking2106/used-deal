package me.devking2106.useddeal.controller;



import static me.devking2106.useddeal.service.BoardService.*;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.controller.results.ApiResult;
import me.devking2106.useddeal.controller.results.Result;
import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.dto.BoardFindDto;
import me.devking2106.useddeal.dto.BoardSaveDto;
import me.devking2106.useddeal.entity.Board;
import me.devking2106.useddeal.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public ResponseEntity<ApiResult> register(@Valid @RequestBody BoardSaveDto boardSaveDto,
		BindingResult bindingResult) throws BindException {
		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		Board boardInfo = boardService.saveBoard(boardSaveDto);
		return Result.created(new ApiResult().add("board", boardInfo));
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResult> findByAll(@RequestParam(required = false) @NotBlank String title,
		@RequestParam(required = false) @NotBlank String content) {
		List<Board> boards = boardService.findByAll(title, content);
		return Result.ok(new ApiResult().add("boards", boards));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResult> findByBoardId(@PathVariable Long id) {
		BoardDetailDto boardInfo = boardService.findById(id);
		boardIsEmpty(boardInfo);
		return Result.ok(new ApiResult().add("board", boardInfo));
	}

	@GetMapping
	public ResponseEntity<ApiResult> findByLocationName(@RequestParam @NotBlank String location,
		@RequestParam(required = false, defaultValue = "0") int range) {
		List<BoardFindDto> boards = boardService.findByAll(location, range);
		return Result.ok(new ApiResult().add("boards", boards));
	}

}
