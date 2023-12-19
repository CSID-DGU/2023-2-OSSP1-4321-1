**2023-1-OPPS1-4321-1**

2023년 2학기 동국대학교 컴퓨터공학과 공개SW프로젝트 1조의 프로젝트입니다.
Akobot은 동국대학교 입시정보를 챗봇으로 제공하는 서비스입니다! 기존 프로젝트에서 몇가지 포인트를 개선하였으며, 아래에서 이전 팀들의 프로젝트를 확인할 수 있습니다. 

[ Akobot.V1 ](https://github.com/junoade/2021-1-OSSP2-ttogttagis-3)

[ Akobot.V2 ](https://github.com/CSID-DGU/2023-1-OSSP1-ProjectMate-2)


## **목차**
 

[ 팀원소개 ](https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/blob/main/README.md#-팀원소개) 

[ 프로젝트 소개 ](https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/blob/main/README.md#-프로젝트-소개) 

[ 아코봇 실행 예시](https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/blob/main/README.md#-아코봇-실행-예시) 

[아코봇 관리자 페이지 화면 구성](https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/blob/main/README.md#%EF%B8%8F-아코봇-관리자-페이지-화면-구성) 

[©️ 라이센스](https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/blob/main/README.md#%EF%B8%8F-라이센스) 

[기술 스택](https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/blob/main/README.md#-기술-스택)


## 팀원 소개

| 이름 | 학번 | 역할 | Major Part | 깃허브 링크 |
| --- | --- | --- | --- | --- |
| 이건우 | 2021110488 | 컴퓨터공학과 | 팀장 | Back-end |  |
| 우지민 | 2021111642 | 컴퓨터공학과 | 팀원 | Back-end |[@jiminw00] https://github.com/jiminw00 |
| 박지은 | 2021111653 | 컴퓨터공학과 | 팀원 | Front-end |  |
| 강신지 | 2021110298 | 컴퓨터공학과 | 팀원 | Front-end |  |

# Akobot *v3.0* 
특정 세부 사항을 요구하는 경우의, 사용자 요구사항에 대해서 적절한 응답을 하도록 하고자
자체적으로 사용자 전처리 부분을 만들어 챗봇 시스템을 구현하였습니다. 그러나 사용자의 입력 토큰이 기존에 설정되어 있는 인텐트와 조금만 달라도 인식되지 않아 답변 성공률이 낮았으며, 저희의 프로젝트는 기존 프로젝트의 장점을 살리되 답변률을 우선 개선시킬 것을 목표로 하였습니다.

## 프로젝트 소개 

> **이전 버전과의 차별점**
> 1. 낮은 매칭률 개선
>>  아코봇 v3.0은 다양한 질문 형식을 고려하여 비교적 모호한 질문에도 답변을 할 수 있도록 인텐트 매칭 알고리즘을 개선하였습니다. 해당 목표를 수행하기 위해 아코봇 v3.0는 이용자 질문을 더 잘 이해할 수있도록 자연어 처리 모델을 사용하였다. 덕분에 아코봇 이용자의 질문 문장이 다양한 형태이더라도 아코봇이 이해하고 적절한 답변을 할 수 있습니다.

> 3. 보안 문제 해결
>> 암호 검증 과정을 클라이언트 사이드가 아닌 서버 사이드로 이동시켰습니다. 뿐만 아니라, 기존에는 관리자 페이지의 비밀번호를 별도로 입력받는 과정이 있음에도 불구하고 페이지 URL을 직접 입력하여 로그인 화면을 우회하고 다른 관리자 페이지에 접근할 수 있었습니다.이에 대한 개선으로, 주요 기능이 있는 페이지로 URL로 직접 접근하는 시도 시 로그인 화면으로 돌아가도록 했습니다.
> 4. UI 개선
>> 사용자가 불편함을 느낄 수 있는 여러 UI를 개선하였습니다.


## 아코봇 실행 예시


## 아코봇 관리자 페이지 화면 구성

<이미지>

> 챗봇 만으로 답변이 해결되지 않을 시, 사용자가 직접 질문을 등록할 수 있는 웹페이지 연동 링크를 제공합니다.

<img width="400" alt="Screenshot 2023-06-19 at 16 13 11" src="https://github.com/CSID-DGU/2023-1-OPPS1-ProjectMate-2/assets/114139700/33bb0116-f5bd-4e3d-b4c6-49c4def128e1">

> 위와 같이 사용자는 미해결 질문을 작성할 수 있고, 이에 대해 관리자가 보낸 답변을 메일로 확인할 수 있습니다. 

## ©️ 라이센스

MIT License

Copyright (c) 2023 CSID DGU ProjectMate

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## 🔧 기술 스택


### Environment
<img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white"/><img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/><img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=flat-square&logo=Visual Studio Code&logoColor=white"/><img src="https://img.shields.io/badge/Intelli%20j-orange?style=flat-square&logo=intellijidea&logoColor=black"/>

### Development
<img src="https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=Python&logoColor=white"/><img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/><img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/><img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>

### Library
<img src="https://img.shields.io/badge/soynlp-0.0.493-brightgreen?style=flat-square&logo=soynlp&logoColor=white"/><img src="https://img.shields.io/badge/pip-23.1.2-blue?style=flat-square&logo=pip&logoColor=white"/><![sbert](https://github.com/CSID-DGU/2023-2-OSSP1-4321-1/assets/121176513/bd7aa88d-bd24-4a0d-be43-77b7a601ba5c)>

### Communication
<img src="https://img.shields.io/badge/Notion-white?style=flat-square&logo=Notion&logoColor=black"/><img src="https://img.shields.io/badge/Webex-black?style=flat-square&logo=Webex&logoColor=white"/>
