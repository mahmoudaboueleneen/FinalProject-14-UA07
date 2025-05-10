package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewRequest extends CommandRequest {
    private UUID productId;
    private UUID userId;
    private int rating;
    private String comment;
}

