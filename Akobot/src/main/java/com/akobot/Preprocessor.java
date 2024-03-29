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
    public List<String> getMatchedIntents(String usr_input) throws IOException, InterruptedException {
        log.info(Paths.get("").toAbsolutePath().toString());
        log.info("USER INPUT -> " + usr_input);

        // 파이썬 모듈이 이미 깔려있는데도 모듈이 없다는 식의 오류가 난다면 python 경로를 명시해주기
        // cmd에 'python -c "import sys; print(sys.executable)"' 입력하면 파이썬 설치 경로 알 수 있음
        ProcessBuilder pb = new ProcessBuilder("python",
                "Akobot/src/main/resources/chatbot/intentmatching.py", usr_input);
                //"src/main/resources/chatbot/intentmatching.py", usr_input);

        Process p = pb.start();

        p.waitFor();

        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        List<String> lines = new ArrayList<>();
        String line = "";

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String errorLine = "";
        while((errorLine = errorReader.readLine()) != null){
            log.error("Python script error: " + errorLine);
        } // 어떤 에러 있는지 확인하려고 추가

        while((line = bfr.readLine()) != null){
            lines.add(line);
        }
        log.info("Preprocessor return lines ->" + lines);

        return lines;
    }

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
