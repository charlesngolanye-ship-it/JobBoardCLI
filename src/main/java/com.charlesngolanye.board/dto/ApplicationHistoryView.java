package com.charlesngolanye.board.dto;

import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.Status;

import java.time.LocalDate;

public record ApplicationHistoryView(Job job, Status status, LocalDate appliedAt)
{
}
