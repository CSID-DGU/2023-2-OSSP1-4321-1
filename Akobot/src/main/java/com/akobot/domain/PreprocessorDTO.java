package com.akobot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreprocessorDTO {
    private String intentName;
    private String level;

    // preprocessor dto ��Ÿ ����
    @Override
    public String toString(){
        return "[intentName : " + intentName + ", level : " + level + "]";
    }
}
