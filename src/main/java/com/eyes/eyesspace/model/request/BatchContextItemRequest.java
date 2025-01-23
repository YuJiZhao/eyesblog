package com.eyes.eyesspace.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BatchContextItemRequest {
    @NotNull(message = "idList不能为空")
    private List<Integer> idList;
}