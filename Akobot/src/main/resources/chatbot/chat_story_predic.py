# 챗봇이 사용자의 입력을 바탕으로 스토리를 추론하는 방법
import csv
import pickle
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
model = SentenceTransformer('jhgan/ko-sroberta-multitask')

import time

# 테스트 케이스는 csv 확장자 형태여야 함
# 테스트 케이스 입력 형식은 다음과 같음
testFile = input("테스트할 파일을 입력해주세요: ")

success = 0
fail = 0

# embedded intent reader
with open('intents.pkl', 'rb') as file:
    user_story = pickle.load(file)

# csv reader
with open(testFile, 'r', encoding='utf-8') as f:
    rdr = csv.reader(f)
    start = time.time()
    output = ""

    # line[0]: 테스트 문장
    # line[1]: 입력 테스트 문장의 인탠트
    for line in rdr:
        inputStr = model.encode(line[0])
        max_similarity = -1
        matched_intent = ""
        for key, value in user_story.items():
            similarity = cosine_similarity([inputStr], [value[3]])
            if(similarity > max_similarity):
                max_similarity = similarity
                matched_intent = value[1]
        # if(max_similarity < 0.65):
        #     matched_intent = "fallback_default"

        if(line[1] != matched_intent):
            result = "fail"
            fail += 1
        else:
            result = "pass"
            success += 1
        output += "\"{0}\",{1},{2},{3},{4}\n".format( result, max_similarity[0][0], line[0], line[1], matched_intent)
    print(output)
    end_time = time.time()
    test_time = end_time - start

    print("******************************")
    print("실행시간: {0}초".format(test_time))
    print("pass: ", success)
    print("fail: ", fail)
    print("success rate: ", success / (success + fail))

    outputFile = ""
    outputFile = input("실행결과를 입력할 파일 위치(종료: ENTER): ")
    if(outputFile != ""):
        with open(outputFile, 'w', encoding='utf-8') as o:
            o.write(output)
            o.close()
        f.close()