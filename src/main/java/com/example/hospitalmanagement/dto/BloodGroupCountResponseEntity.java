package com.example.hospitalmanagement.dto;

import com.example.hospitalmanagement.entity.type.BloodGroupType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BloodGroupCountResponseEntity{
    private BloodGroupType bloodGroupType;
    private Long count;
}