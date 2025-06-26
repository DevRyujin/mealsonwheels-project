package com.merrymeal.mealsonwheels.service.admin;

import com.merrymeal.mealsonwheels.dto.FoodSafetyRecordDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.FoodSafetyRecord;
import com.merrymeal.mealsonwheels.model.Ingredient;
import com.merrymeal.mealsonwheels.repository.FoodSafetyRecordRepository;
import com.merrymeal.mealsonwheels.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodSafetyRecordServiceImpl implements FoodSafetyRecordService {

    private final FoodSafetyRecordRepository recordRepository;
    private final IngredientRepository ingredientRepository;

    public FoodSafetyRecordServiceImpl(FoodSafetyRecordRepository recordRepository,
                                       IngredientRepository ingredientRepository) {
        this.recordRepository = recordRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public FoodSafetyRecordDTO saveRecord(FoodSafetyRecordDTO dto) {
        FoodSafetyRecord record = dtoToEntity(dto);
        FoodSafetyRecord saved = recordRepository.save(record);
        return entityToDto(saved);
    }

    @Override
    public Optional<FoodSafetyRecordDTO> getRecordById(Long id) {
        return recordRepository.findById(id).map(this::entityToDto);
    }

    @Override
    public List<FoodSafetyRecordDTO> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodSafetyRecordDTO> getRecordsByIngredientId(Long ingredientId) {
        return recordRepository.findByIngredientId(ingredientId).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoodSafetyRecordDTO updateRecord(Long id, FoodSafetyRecordDTO dto) {
        FoodSafetyRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodSafetyRecord not found with id " + id));

        if (dto.getInspectionDate() != null)
            record.setInspectionDate(dto.getInspectionDate());

        if (dto.getSafetyChecklistCompleted() != null)
            record.setSafetyChecklistCompleted(dto.getSafetyChecklistCompleted());

        if (dto.getInspectionNotes() != null)
            record.setInspectionNotes(dto.getInspectionNotes());

        if (dto.getIngredientId() != null) {
            Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id " + dto.getIngredientId()));
            record.setIngredient(ingredient);
        }

        FoodSafetyRecord updated = recordRepository.save(record);
        return entityToDto(updated);
    }

    @Override
    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    // Conversion helpers
    private FoodSafetyRecord dtoToEntity(FoodSafetyRecordDTO dto) {
        FoodSafetyRecord record = new FoodSafetyRecord();
        record.setId(dto.getId());
        record.setInspectionDate(dto.getInspectionDate());
        record.setSafetyChecklistCompleted(dto.getSafetyChecklistCompleted() != null && dto.getSafetyChecklistCompleted());
        record.setInspectionNotes(dto.getInspectionNotes());

        if (dto.getIngredientId() != null) {
            Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id " + dto.getIngredientId()));
            record.setIngredient(ingredient);
        }
        return record;
    }

    private FoodSafetyRecordDTO entityToDto(FoodSafetyRecord record) {
        FoodSafetyRecordDTO dto = new FoodSafetyRecordDTO();
        dto.setId(record.getId());
        dto.setInspectionDate(record.getInspectionDate());
        dto.setSafetyChecklistCompleted(record.isSafetyChecklistCompleted());
        dto.setInspectionNotes(record.getInspectionNotes());
        dto.setIngredientId(record.getIngredient() != null ? record.getIngredient().getId() : null);
        return dto;
    }
}
