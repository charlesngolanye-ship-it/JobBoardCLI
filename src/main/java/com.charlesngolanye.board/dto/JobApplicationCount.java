package com.charlesngolanye.board.dto;

import com.charlesngolanye.board.model.Job;

public record JobApplicationCount(
        Job job,
        int applicationCount
) {
}
