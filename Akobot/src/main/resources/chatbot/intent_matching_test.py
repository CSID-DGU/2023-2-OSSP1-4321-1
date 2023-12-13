# pandas랑 sentence-transformers를 먼저 깔아야됨
# 터미널에 다음 명령어를 입력해서 라이브러리 먼저 깔아주세요
# pip install pandas
# pip install setence-transformers

import csv
import pickle
import pickle
import pandas as pd
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
with open('./src/intents.p', 'rb') as file:
    intents = pickle.load(file)

# csv reader
with open(testFile, 'r', encoding='utf-8') as f:
    rdr = csv.reader(f)

    start = time.time()
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
        print(result, ", ", max_similarity[0][0], ", ", line[1], ", " + matched_intent, ", ", line[0], sep="")

    start = time.time() - start

    print("******************************")
    print("pass: ", success)
    print("fail: ", fail)
    print("실행시간: ", start)