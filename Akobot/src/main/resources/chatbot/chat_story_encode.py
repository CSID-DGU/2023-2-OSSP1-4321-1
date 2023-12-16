# 사용자 스토리 임베딩
import csv
import pickle
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
model = SentenceTransformer('jhgan/ko-sroberta-multitask')

story = {
    # level 1
    '전체모집요강에 대해 알려줘.': ['전체모집요강', 'total', 1],
    '수시에 대해 알려줘.': ['수시', 'earlyadmission', 1],
    '정시에 대해 알려줘.': ['정시', 'ksat', 1],
    '모집인원에 대해 알려줘.': ['모집인원', 'recruit', 1],
    '작년도경쟁률에 대해 알려줘.': ['작년도경쟁률', 'competition', 1],
    '지원자격유의사항에 대해 알려줘.': ['지원자격유의사항', 'note', 1],
    '외국인특별전형에 대해 알려줘.': ['외국인특별전형', 'foreign', 1],
    '전형요소별평가방법에 대해 알려줘.': ['전형요소별평가방법', 'test', 1],
    '전형일정에 대해 알려줘.': ['전형일정', 'schedule', 1],

    # level 3
    '두드림소프트웨어에 대해 알려줘.': ['두드림소프트웨어', 'earlyadmission_dodreamsoft', 3],
    '불교추천에 대해 알려줘.': ['불교추천', 'earlyadmission_buddhism', 3],
    '학교장추천에 대해 알려줘.': ['학교장추천', 'earlyadmission_principal', 3],
    '논술에 대해 알려줘.': ['논술', 'earlyadmission_essay', 3],
    '두드림에 대해 알려줘.': ['두드림', 'earlyadmission_dodream', 3],
    '고른기회에 대해 알려줘.': ['고른기회', 'earlyadmission_regular', 3],
    '특수교육대상자에 대해 알려줘.': ['특수교육대상자', 'earlyadmission_special', 3],
    '수시재직자전형에 대해 알려줘.': ['재직자', 'earlyadmission_incumbent', 3],
    '실기에 대해 알려줘.': ['실기', 'earlyadmission_performance', 3],

    # level 4
    '일반전형에 대해 알려줘.': ['일반전형', 'ksat_normal', 4],
    '농어촌전형에 대해 알려줘.': ['농어촌전형', 'ksat_farming', 4],
    '특성화고교에 대해 알려줘.': ['특성화고교', 'ksat_specialized', 4],
    '정시재직자전형에 대해 알려줘.': ['재직자', 'ksat_incumbent', 4],
    '기초생활수급자및차상위계층에 대해 알려줘.': ['기초생활수급자및차상위계층', 'ksat_basic', 4],

    # level 6
    '수시경쟁률에 대해 알려줘.': ['수시경쟁률', 'competition_earlyadmission', 6],
    '정시경쟁률에 대해 알려줘.': ['정시경쟁률', 'competition_ksat', 6],

    # level 7
    '농어촌학생재학거주인정기준에 대해 알려줘.': ['농어촌학생재학거주인정기준', 'note_farming', 7],
    '특성화고교졸업자동일계열기준학과에 대해 알려줘.': ['특성화고교졸업자동일계열기준학과', 'note_special', 7],
    '재직기간산정기준에 대해 알려줘.': ['재직기간산정기준', 'note_incumbent', 7],

    # level 9
    '서류종합평가에 대해 알려줘.': ['서류종합평가', 'test_document', 9],
    '면접평가에 대해 알려줘.': ['면접평가', 'test_interview', 9],
    '학교생활기록부에 대해 알려줘.': ['학교생활기록부', 'test_records', 9],
    '대학수학능력시험에 대해 알려줘.': ['대학수학능력시험', 'test_SAT', 9],

    # level 10
    '외국인일정에 대해 알려줘.': ['재외국민/외국인일정', 'schedule_foreigner', 10],
    '재외국민에 대해 알려줘.': ['재외국민/외국인일정', 'schedule_foreigner', 10],

    '정시일정에 대해 알려줘.': ['정시일정', 'schedule_ksat', 10],
    '수시일정에 대해 알려줘.': ['수시일정', 'schedule_earlyadmission', 10],

    # fallback intent
    # '잘못된 입력':['fallback',0]
    '잘못된 입력': ['잘못된 입력', 'fallback_default', 0],


#키워드 타입
    #level 1
    '전체모집요강':['total',1],
    '수시':['earlyadmission',1],
    '정시':['ksat',1],
    '모집인원':['recruit',1],
    '작년도경쟁률':['competition',1],
    '지원자격유의사항':['note',1],
    '외국인특별전형':['foreign',1],
    '전형요소별평가방법':['test',1],
    '전형일정':['schedule',1],
    #level 3
    '두드림소프트웨어':['earlyadmission_dodreamsoft',3],
    '불교추천':['earlyadmission_buddhism',3],
    '학교장추천':['earlyadmission_principal',3],
    '논술':['earlyadmission_essay',3],
    '두드림':['earlyadmission_dodream',3],
    '고른기회':['earlyadmission_regular',3],
    '특수교육대상자':['earlyadmission_special',3],
    '수시재직자':['earlyadmission_incumbent',3],
    '실기':['earlyadmission_performance',3],
    #level 4
    '일반전형':['ksat_normal',4],
    '농어촌전형':['ksat_farming',4],
    '특성화고교':['ksat_specialized',4],
    '정시재직자':['ksat_incumbent',4],
    '기초생활수급자및차상위계층':['ksat_basic',4],
    #level 6
    '수시경쟁률':['competition_earlyadmission',6],
    '정시경쟁률':['competition_ksat',6],
    #level 7
    '농어촌학생재학거주인정기준':['note_farming',7],
    '특성화고교졸업자동일계열기준학과':['note_special',7],
    '재직기간산정기준':['note_incumbent',7],
    #level 9
    '서류종합평가':['test_document',9],
    '면접평가':['test_interview',9],
    '학교생활기록부':['test_records',9],
    '생기부':['test_records',9],
    '대학수학능력시험':['test_SAT',9],
    '수능':['test_SAT',9],
    #level 10
    '재외국민일정':['schedule_foreigner',10],
    '외국인일정':['schedule_foreigner',10],
    '정시일정':['schedule_ksat',10],
    '수시일정':['schedule_earlyadmission',10]
}

# 사용자 스토리를 임베딩
for key, value in story.items():
    story[key].append(model.encode(key))

# 결과 확인
for key, value in story.items():
    print(key, value)

# 임베딩된 객체를 바이너리 파일로 저장
with open("user_story.pkl", 'wb') as file:
    pickle.dump(story, file)
