package me.devking2106.useddeal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.controller.request.BoardFindRequest;
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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/boards")
	public Board register(@Valid @RequestBody BoardSaveDto boardSaveDto,
		BindingResult bindingResult) throws BindException {
		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		Board boardInfo = boardService.saveBoard(boardSaveDto);
		return boardInfo;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/boards/{id}")
	public BoardDetailDto findById(@PathVariable Long id) {
		BoardDetailDto boardInfo = boardService.findById(id);
		return boardInfo;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/boards")
	public List<BoardFindDto> findAll(@Valid BoardFindRequest boardFindRequest) {
		List<BoardFindDto> boards = boardService.findAll(boardFindRequest);
		return boards;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/{userId}/boards")
	public List<BoardFindDto> findByUser(@PathVariable Long userId) {
		List<BoardFindDto> boards = boardService.findByUser(userId);
		return boards;
	}

}
