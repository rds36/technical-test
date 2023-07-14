package com.dans.multipro.technicaltest.service;

import com.dans.multipro.technicaltest.data.model.Position;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface PositionService {
    List<Position> getAllPosition();
    Position getPositionById(UUID id) ;
}
