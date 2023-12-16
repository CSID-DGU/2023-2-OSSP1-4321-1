import sys
import pickle
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

# 임시!!!!! usr_input="수시 논술 알려줘"
usr_input = sys.argv[1]

model = SentenceTransformer('jhgan/ko-sroberta-multitask')


# 매칭된 [인텐트 영문명, 인텐트 레벨]를 갖는 2D 리스트
matchings = []

# intent embedding vector reader
with open('user_story.pkl', 'rb') as file:
    intents = pickle.load(file)

    # user_story.pkl 구조:
    # itents = {
    # "intent sentence" : ['intent', 'intent-eng', 'level' ],
    # ...
    # }


    inputStr = model.encode(usr_input)
    max_similarity = -1
    matched_intent = ""
    matched_level = -1
    for key, value in intents.items():
        similarity = cosine_similarity([inputStr], [value[3]])
        if (similarity > max_similarity):
            max_similarity = similarity
            matched_intent = value[1]
            matched_level = value[2]
    # 최대 유사도가 0.65 이하이면 fallback_default로 처리
    if(max_similarity < 0.65):
        matched_intent = "fallback_default"
        matched_level = 0

    matchings.add(matched_intent)
    matchings.add(matched_level)

# 매칭된 인텐트 중 포함관계 존재시, 상위인텐트 제거
'''
# 매칭 결과에 1과 2이상의 레벨 인텐트가 존재하는 경우,
# 둘의 이름을 비교하여 직속 상/하위 여부 판단 후,
# 만약 직속 상관관계를 가진다면 레벨이 1인 인텐트를 제거
'''
# for element in matchings:
#     if element[1] == 1:  # 레벨이 1
#         for another in matchings:
#             if another[1] > 1 and (element[0] in another[0]):
#                 matchings.remove(element)

# 최종 매칭된 인텐트 [인텐트 영문명, 영어 레벨] 출력
print(matchings)
