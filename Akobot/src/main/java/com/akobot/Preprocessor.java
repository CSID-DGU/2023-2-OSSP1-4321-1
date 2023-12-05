package com.akobot;

import com.akobot.domain.PreprocessorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class Preprocessor {
    private String removeSpaces(String input) {
        // 띄어쓰기를 제거하는 함수
        return input.replaceAll("\\s", "");
    }

    public List<String> getMatchedIntents(String usr_input) throws IOException, InterruptedException {
        log.info(Paths.get("").toAbsolutePath().toString());
        // 띄어 쓰기 제거 함수 호출
        String processedInput = removeSpaces(usr_input);

        // 띄어 쓰기를 제거한 값이 토크나이저로 넘어감
        log.info("PROCESSED USER INPUT -> " + processedInput);
        // python 프로세스
        ProcessBuilder pb = new ProcessBuilder("python",
                "Akobot/src/main/resources/chatbot/intentmatching.py", processedInput);

        Process p = pb.start();

        p.waitFor();

        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        List<String> lines = new ArrayList<>();
        String line = "";


        while ((line = bfr.readLine()) != null) {
            lines.add(line);
        }
        log.info("Preprocessor return lines ->" + lines);

        return lines;
    }   //python 전처리 스크립트 수행 완료

    // python에서 '수시일정':['schedule_earlyadmission',10] 꼴로 인탠트까지 나오는 걸 받아서
    // [intentName : schedule_earlyadmission, level : 10]  리스트 꼴로 저장한다.
    public List<PreprocessorDTO> toArray(List<String> linesFromPython){
        List<PreprocessorDTO> intents = new ArrayList<>();
        String intentName = "";
        String level = "";

        for(String l : linesFromPython){
            char[] c = l.toCharArray();
            int brackets = 0;

            for(int i = 0; i < c.length; ){
                if(c[i] == '['){
                    brackets++;
                    i++;
                }
                else if(c[i] == ']'){
                    brackets--;
                    i++;
                }
                else if(c[i] == '\'' && brackets == 2){
                    i++;

                    intentName = "";
                    level = "";

                    PreprocessorDTO tmpDTO = new PreprocessorDTO();

                    while(c[i] != '\''){
                        intentName += c[i];
                        i++;
                    }
                    log.info(intentName);

                    if(c[i] == '\'') i++;

                    if(c[i] == ',') i++;

                    if(c[i] == ' ') i++;

                    while(c[i] != ']'){
                        level += c[i];
                        i++;
                    }
                    log.info(level);

                    tmpDTO.setIntentName(intentName);
                    tmpDTO.setLevel(level);

                    intents.add(tmpDTO);
                }
                else i++;
            }
        }

        return intents;
    }
}
