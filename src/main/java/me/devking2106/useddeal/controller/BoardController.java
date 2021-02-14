package me.devking2106.useddeal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
	public Board save(@Valid @RequestBody BoardSaveDto boardSaveDto) {
		return boardService.saveBoard(boardSaveDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/boards/{id}")
	public BoardDetailDto findById(@PathVariable Long id) {
		return boardService.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/boards")
	public List<BoardFindDto> findAll(@Valid BoardFindRequest boardFindRequest) {
		return boardService.findAll(boardFindRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/{userId}/boards")
	public List<BoardFindDto> findByUser(@PathVariable Long userId) {
		return boardService.findByUser(userId);
	}

}