import sys
import pickle
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

usr_input = sys.argv[1]
# usr_input = input("사용자 문장: ")

model = SentenceTransformer('jhgan/ko-sroberta-multitask')

# 매칭된 [인텐트 영문명, 인텐트 레벨]를 갖는 2D 리스트
matchings = []

# intent embedding vector reader
with open('/Akobot/src/main/resources/chatbot/intents.pkl', 'rb') as file:
    intents = pickle.load(file)

    # intents.pkl 구조:
    # itents = {
    # "intent sentence" : ['intent', 'intent-eng', 'level', embedding vector],
    # ...
    # }


    input_str = model.encode(usr_input)
    max_similarity = -1
    matched_intent = ""
    matched_level = -1
    for key, value in intents.items():
        similarity = cosine_similarity([input_str], [value[3]])
        if (similarity > max_similarity):
            max_similarity = similarity
            matched_intent = value[1]
            matched_level = value[2]
    # 최대 유사도가 0.65 이하이면 fallback_default로 처리
    if(max_similarity < 0.65):
        matched_intent = "fallback_default"
        matched_level = 0

    matchings.append(matched_intent)
    matchings.append(matched_level)

# 최종 매칭된 인텐트 [인텐트 영문명, 영어 레벨] 출력
print(matchings)
