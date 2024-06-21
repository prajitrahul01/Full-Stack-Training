package org.example.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Data

@Table(name="break_table")

public class Break {

    @Id

    @GeneratedValue

    @Column(name="break_id")

    private int breakId;

    @Column(name="start_time",nullable = false)

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")

    private LocalDateTime breakStartTime;

    @Column(name="end_time",nullable = false)

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")

    private LocalDateTime breakEndTime;

    @Column(name="meal_break",columnDefinition = "boolean default false")

    private boolean mealBreak;

    @ManyToOne

    @JoinColumn(name="assigned_shift")

    private Shift shift;
}

 