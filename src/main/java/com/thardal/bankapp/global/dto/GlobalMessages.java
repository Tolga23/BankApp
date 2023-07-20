package com.thardal.bankapp.global.dto;

import lombok.Data;

import java.util.List;

@Data
public class GlobalMessages {

    private List<GlobalMessage> errorMessages;
    private List<GlobalMessage> infoMessages;
    private List<GlobalMessage> warningMessages;
}
