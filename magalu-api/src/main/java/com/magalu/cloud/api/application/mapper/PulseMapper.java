package com.magalu.cloud.api.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.magalu.cloud.api.application.DTO.PulseDTO;
import com.magalu.cloud.api.application.DTO.SummarizedDTO;
import com.magalu.cloud.api.domain.model.Pulse;
import com.magalu.cloud.api.domain.model.Summarized;

@Mapper(componentModel = "spring")
public interface PulseMapper {
    PulseMapper INSTANCE = Mappers.getMapper(PulseMapper.class);

    PulseDTO toPulseDTO(Pulse pulse);
    Pulse toPulse(PulseDTO pulseDTO);

    List<SummarizedDTO> toSummarizedDTO(List<Summarized> summarizedList);
}
