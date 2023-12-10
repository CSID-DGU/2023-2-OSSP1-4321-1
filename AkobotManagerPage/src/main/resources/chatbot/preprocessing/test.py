# 라이브러리
import pandas as pd
from soynlp import DoublespaceLineCorpus
from soynlp.word import WordExtractor
from soynlp.tokenizer import MaxScoreTokenizer

import csv
from collections import Counter

# 스코어링 함수
def scoring(corpus_path):
    # 텍스트 파일의 라인 수 세기
    corpus = DoublespaceLineCorpus(corpus_path)

    # 코퍼스 훈련/문장 주요 단어 추출
    word_extractor = WordExtractor()
    word_extractor.train(corpus)
    word_score_table = word_extractor.extract()

    # cohesion forward 점수 추출(단어 결합)
    scores = {word: score.cohesion_forward for word, score in word_score_table.items()}
    print(scores)

    # 점수를 CSV 파일로 저장
    with open("scores.csv", 'w', newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerow(["Word", "Score"])  # 헤더 작성
        for k, v in scores.items():
            writer.writerow([k, f"{v:.6f}"])  # 점수를 소수점 6자리까지 형식으로 저장

    # 추출 부분에서 활용할 수 있도록 리턴
    return word_score_table

# 추출 부분
def extracting(usr_text, word_score_table):
    maxscore_tokenizer = MaxScoreTokenizer(scores={word: score.cohesion_forward for word, score in word_score_table.items()})

    # 사용자가 입력한 텍스트를 maxscore_tokenizer.tokenize으로 토큰화
    tokenized_texts = [maxscore_tokenizer.tokenize(usr_text)]

    # 모든 토큰을 flatten하여 하나의 리스트로 만듬
    all_tokens = [token for tokens in tokenized_texts for token in tokens]
    
    print(all_tokens)

    # 중요한 토큰에 대한 응집도 점수 출력
    print("\n토큰의 응집도 점수가  나와있는 토큰:")
    important_cohesion_scores = {}
    for token in all_tokens:
        if token in word_score_table:
            cohesion_score = word_score_table[token].cohesion_forward
            print(f"{token}: {cohesion_score}")
            important_cohesion_scores[token] = cohesion_score

# 사용자가 입력한 텍스트
usr_text = "컴공정시랑수시논술생기부모집인원이어떻게되니"

# 스코어링 함수 실행
word_score_table = scoring("training.txt")

# 중요한 토큰 추출
N = 10
important_tokens = sorted(word_score_table, key=lambda x: word_score_table[x].cohesion_forward, reverse=True)[:N]

# 추출 부분 실행
extracting(usr_text, word_score_table)
