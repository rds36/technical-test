package com.dans.multipro.technicaltest.controller;

import com.dans.multipro.technicaltest.data.model.Position;
import com.dans.multipro.technicaltest.service.PositionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getAllPositions() throws JsonProcessingException {
        return new ResponseEntity<>(positionService.getAllPosition(), HttpStatus.OK);
    }

    @GetMapping("/position/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable String id) throws JsonProcessingException {
        return new ResponseEntity<>(positionService.getPositionById(UUID.fromString(id)),HttpStatus.OK);
    }
}
