package org.example.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "shift_model")
public class Shift {
    @Column(name = "shift_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shiftId;
    @Column(name = "job", nullable = false)
    @NotBlank(message = "Shift job description is required")
    private String shiftJob;
    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time can't be null")
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "Enter a Valid Start Time")
    private LocalDateTime shiftStartTime;
    @Column(name = "end_time", nullable = false)
    @NotNull(message = "End time can't be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shiftEndTime;
    @Column(name = "requested_user_id")
    private Integer requested_user_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @AssertTrue(message = "Start time must be before end time")
    public boolean isValidShiftTimeRange() {
        if (shiftStartTime != null && shiftEndTime != null) {
            return shiftStartTime.isBefore(shiftEndTime);
        }
        return true;
    }
}
