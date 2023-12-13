# pandas랑 sentence-transformers를 먼저 깔아야됨
# 터미널에 다음 명령어를 입력해서 라이브러리 먼저 깔아주세요
# pip install pandas
# pip install setence-transformers

import csv
import pickle
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
model = SentenceTransformer('jhgan/ko-sroberta-multitask')

import time

# 테스트 케이스는 csv 확장자 형태여야 함
# 테스트 케이스 입력 형식은 다음과 같음
# <입력문장, 인탠트>
testFile = input("테스트할 파일을 입력해주세요: ")

success = 0
fail = 0

# embedded intent reader
with open('./intents.p', 'rb') as file:
    intents = pickle.load(file)

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
        for key, value in intents.items():
            similarity = cosine_similarity([inputStr], [value[2]])
            if(similarity > max_similarity):
                max_similarity = similarity
                matched_intent = value[0]

        if(line[1] != matched_intent):
            result = "fail"
            fail += 1
        else:
            result = "pass"
            success += 1
        # 결과: <입력, 인탠트, 매칭된 인탠트, 통과여부, 유사도>
        output += "\"{0}\", {1}, {2}, {3}, {4}\n".format(line[0], line[1], matched_intent, result, max_similarity[0][0])
    print(output)
    end_time = time.time()
    test_time = end_time - start

    print("******************************")
    print("실행시간: {0}초".format(test_time))
    print("pass: ", success)
    print("fail: ", fail)
    print("success rate: ", success / (success + fail))
    print("실행결과: test_result.csv")

    with open("../../../../../test/test_result.csv", 'w', encoding='utf-8') as o:
        o.write(output)
        o.close()
    f.close()