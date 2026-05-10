package com.charlesngolanye.board.dto;

import com.charlesngolanye.board.model.Applicant;
import com.charlesngolanye.board.model.Status;

import java.time.LocalDate;

public record ApplicantApplicationView(
        Applicant applicant,
        Status status,
        LocalDate appliedAt
) {
}
