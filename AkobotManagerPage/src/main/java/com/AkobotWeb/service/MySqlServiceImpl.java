package com.AkobotWeb.service;

import com.AkobotWeb.domain.BoardVO;
import com.AkobotWeb.domain.DB.IntentDTO;
import com.AkobotWeb.domain.DB.tables.AskSolEntity;
import com.AkobotWeb.domain.Mail.MailDTO;
import com.AkobotWeb.domain.SMS.SMSDTO;
import com.AkobotWeb.domain.SolveVO.SolveVO;
import com.AkobotWeb.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MySqlServiceImpl implements MySqlService {
    private final AskSolRepository askSolRepo;
    private final EtcRepository etcRepo;
    private final KsatRepository ksatRepositoryRepo;
    private final EarlyAdmissionRepository earlyAdmissionRepositoryRepo;
    private final TestRepository testRepo;

    /*public static final String COLLECTION_NAME="TBL_BOARD"; // cloud firestore의 collection name*/
    //public static final String COLLECTION_NAME = "TBL_ASK_DUMMY"; // cloud firestore의 collection name
    public static final String COLLECTION_NAME = "TBL_ASK";
    public static final String SOLVE_COLLECTION = "TBL_SOLVED";

    /*@Override
    public BoardVO getMemberDetail(String name) throws Exception {
        firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(name);
        ApiFuture<DocumentSnapshot> apiFuture = docRef.get();
        DocumentSnapshot docShot = apiFuture.get();

        BoardVO member = null;
        if (docShot.exists()) {
            member = docShot.toObject(BoardVO.class);
            return member;
        } else {
            return null;
        }
    }*/

    /**
     * 미해결 질문 게시판 관련
     */
    /* 0503 질문 게시글 전체 조회 기능 구현 */
    @Override
    public List<BoardVO> getBoardVO() throws Exception {
        /*System.out.println("getboard");*/
        List<BoardVO> list = new ArrayList<>();
        //firestore = FirestoreClient.getFirestore();

        //ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        //List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<AskSolEntity> boards = askSolRepo.unsolvedAll();
        log.info("Get unsolved from database: " + boards.getClass() + "->" + boards);

        for (AskSolEntity board : boards) {
            // logger 이용하는 방식으로 바꿔주기
            /*System.out.println(document.getId() + " -> "+ document.toObject(BoardVO.class));*/
            log.info(board.getClass() + " -> " + board);
            list.add(board.toBoardVO());
        }

        return list;
    }

    /* 0510 질문 상세 페이지 조회 구현 */
    @Override
    public BoardVO read(Long bno) throws Exception {
        //firestore = FirestoreClient.getFirestore();
        BoardVO boardVO;
        // Create a reference to the collection
        //CollectionReference ref = firestore.collection(COLLECTION_NAME);

        // Create a query against the collection.
        //Query query = ref.whereEqualTo("bno", bno);

        // retrieve  query results asynchronously using query.get()
        //ApiFuture<QuerySnapshot> querySnapshot = query.get();
        //DocumentSnapshot document = querySnapshot.get().getDocuments().get(0); // 리스트의 0번쨰 요소를 가져오도록

        boardVO = askSolRepo.findByBno(bno).toBoardVO();
        log.info("Get Ask data detail: " + boardVO.getClass() + " -> " + boardVO);

        /* TODO log */
        /*System.out.println(boardVO.toString());*/

        return boardVO;
    }

    /* 0510 mkDummy */
    /*@Override
    public void mkDummy() {
        firestore = FirestoreClient.getFirestore();
        for (int i = 1; i <= 25; i++) {
            BoardVO boardVO = new BoardVO(Integer.toUnsignedLong(i), "dummy content", "김철수","example@akobot.com", Timestamp.now());
            ApiFuture<DocumentReference> future = firestore.collection(COLLECTION_NAME).add(boardVO);
        }
        System.out.println("Done");
    }*/

    /* bno 읽어와 bno 갱신한 새 도큐먼트 DB에 삽입*/
    @Override
    public void add(@ModelAttribute BoardVO board) throws Exception {

        /* 마지막 bno을 읽어와 ++bno 값으로 새 도큐먼트를 DB에 삽입*/
        long bno = getBno();
        board.setBno(++bno);
        board.setAskDate(ZonedDateTime.now());

        //ApiFuture<DocumentReference> future = firestore.collection(COLLECTION_NAME).add(board);
        askSolRepo.save(board);
    }

    /* getBno */
    @Override
    public long getBno() throws Exception {
        /*
        firestore = FirestoreClient.getFirestore();
        // Create a reference to the collection
        CollectionReference ref = firestore.collection(COLLECTION_NAME);

        // Create a query against the collection.
        Query query = ref.orderBy("bno", Query.Direction.DESCENDING).limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
        return document.getLong("bno");
         */

        return askSolRepo.maxBno();
    }
    /* getSolvedBno */

    /**
     * 해결질문게시판 관련
     */
    /* 해결 질문 게시판 전체 조회 */
    @Override
    public List<SolveVO> getSolveVO() throws Exception {
        log.info("해결 질문 게시판 조회 처리 시작");
        List<SolveVO> list = new ArrayList<>();
        /*firestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = firestore.collection(SOLVE_COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            log.info(document.getId() + " -> " + document.toObject(SolveVO.class));
            list.add(document.toObject(SolveVO.class));
        }*/

        List<AskSolEntity> askSolEntities = askSolRepo.solvedAll();
        log.info("Get solvedAll() from database: " + askSolEntities.getClass() + "->" + askSolEntities);

        for(AskSolEntity askSol : askSolEntities){
            log.info(askSol.getClass() + " -> " + askSol);
            list.add(askSol.toSolveVO());
        }

        log.info("해결 질문 게시판 전체 조회 완료");
        return list;
    }

    /* 해결 질문 게시판 개별 조회 */
    @Override
    public SolveVO readSolve(Long bno) throws Exception {
        //firestore = FirestoreClient.getFirestore();
        SolveVO solveVO;
        /*
        // Create a reference to the collection
        CollectionReference ref = firestore.collection(SOLVE_COLLECTION);

        // Create a query against the collection.
        Query query = ref.whereEqualTo("bno", bno);

        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        DocumentSnapshot document = querySnapshot.get().getDocuments().get(0); // 리스트의 0번쨰 요소를 가져오도록

        solveVO = document.toObject(SolveVO.class);
         */

        AskSolEntity askSol = askSolRepo.findByBno(bno);
        solveVO = askSol.toSolveVO();

        log.info("해결 상세 질문 조회  " + solveVO.toString());
        return solveVO;
    }

    /* TODO 미해결 -> 해결 질문 게시판으로 처리 - smsService 메소드에서 호출 */
    @Override
    public void migrateSMS(SMSDTO smsdto, Long bno) throws Exception {
        //I. 미해결 질문 게시판으로 부터
        // 1) 해당 bno를 갖는 BoardVO 객체 정보 가져온다
        /*
        firestore = FirestoreClient.getFirestore();
        CollectionReference ref = firestore.collection(COLLECTION_NAME);
        Query query = ref.whereEqualTo("bno", bno);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        DocumentSnapshot document = querySnapshot.get().getDocuments().get(0); // 리스트의 0번쨰 요소를 가져오도록

        // 2) SMSDTO로 부터 cloud firstore에 저장되는 SolveVO 인스턴로 옮긴다
        SolveVO temp = document.toObject(SolveVO.class);
        long sol_bno = getSolBno();
        temp.setBno(++sol_bno);
        temp.setAnswer(smsdto.getMsg());
        temp.setAnswerDate(Timestamp.now());
        log.info("migrate stemp 2 - SolveVO instance info : " + temp.toString());

        // 3) 기존의 DB컬렉션에서 해당 도큐먼트 삭제한다
        String docName = document.getId();
        log.info("migrate step 3 - docRef : " + docName);
        ApiFuture<WriteResult> writeResult = ref.document(docName).delete(); // asynchronously delete a document

        // 4) 해당 DB의 콜렉션에 새 도큐먼트로 추가한다
        ref = firestore.collection(SOLVE_COLLECTION);
        ApiFuture<DocumentReference> future = ref.add(temp);
        log.info("migrate step 4 - 옮기기 완료");
         */

        // SMSDTO로 부터 ask_sol table에 저장되는 SolveVO 인스턴로 옮긴다
        SolveVO tempSol = askSolRepo.findByBno(bno).toSolveVO();
        tempSol.setAnswer(smsdto.getMsg());
        tempSol.setAnswerDate(ZonedDateTime.now());
        askSolRepo.updateSol(tempSol);

    }
    @Override
    public void migrateEmail(MailDTO mailDTO, Long bno) throws Exception {
        /*
        // 1) 미해결 질문 게시판으로 부터 해당 bno를 갖는 BoardVO 객체 정보 가져온다
        firestore = FirestoreClient.getFirestore();
        CollectionReference ref = firestore.collection(COLLECTION_NAME);
        Query query = ref.whereEqualTo("bno", bno);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        DocumentSnapshot document = querySnapshot.get().getDocuments().get(0); // 리스트의 0번쨰 요소를 가져오도록

        // 2) MailDTO 부터 cloud firstore에 저장되는 SolveVO 인스턴로 옮긴다
        SolveVO temp = document.toObject(SolveVO.class);
        long sol_bno = getSolBno();
        temp.setBno(++sol_bno);
        temp.setAnswer(mailDTO.getMessage());
        temp.setAnswerDate(Timestamp.now());
        log.info("migrate stemp 2 - SolveVO instance info : " + temp.toString());

        // 3) 기존의 DB컬렉션에서 해당 도큐먼트 삭제한다
        String docName = document.getId();
        log.info("migrate step 3 - docRef : " + docName);
        ApiFuture<WriteResult> writeResult = ref.document(docName).delete(); // asynchronously delete a document

        // 4) 해당 DB의 콜렉션에 새 도큐먼트로 추가한다
        ref = firestore.collection(SOLVE_COLLECTION);
        ApiFuture<DocumentReference> future = ref.add(temp);
        log.info("migrate step 4 - 옮기기 완료");
         */

        // MailDTO로 부터 ask_sol table에 저장되는 SolveVO 인스턴로 옮긴다
        SolveVO tempSol = askSolRepo.findByBno(bno).toSolveVO();
        tempSol.setAnswer(mailDTO.getMessage());
        tempSol.setAnswerDate(ZonedDateTime.now());

        log.info("temp SolveVO: " + tempSol.toString());

        askSolRepo.updateSol(tempSol);

    }
    /*@Override
    public long getSolBno() throws Exception {
        firestore = FirestoreClient.getFirestore();

        CollectionReference ref = firestore.collection(SOLVE_COLLECTION);

        Query query = ref.orderBy("bno", Query.Direction.DESCENDING).limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);

        return document.getLong("bno");

        //return askSolRepo.maxBno();
    }*/

    /**
     * 관리자 챗봇 DB 수정 관련
     */
    @Override
    public void updateDB(String field, String doc, String update) throws Exception {
        /*
        firestore = FirestoreClient.getFirestore();
        // Update an existing document
        DocumentReference docRef = firestore.collection(collection).document(doc);

        // (async) Update one field
        ApiFuture<WriteResult> future = docRef.update("elseData", update);

        // ...
        WriteResult result = future.get();
        log.info("챗봇 DB 수정 완료 : " + result);
         */

        log.info("Field: " + field
        + "doc: " + doc, "update: " + update);

        IntentDTO intentDTO = new IntentDTO();

        if((intentDTO = etcRepo.findOne(11111, field, doc)) != null){
            etcRepo.update(intentDTO.getPks(), update);
        }
        else if((intentDTO = earlyAdmissionRepositoryRepo.findOne(11111, field, doc)) != null){
            log.info("Update " + intentDTO.getElseData() + " -> " + update);
            earlyAdmissionRepositoryRepo.update(intentDTO.getPks(), update);
        }
        else if((intentDTO = ksatRepositoryRepo.findOne(11111, field, doc)) != null){
            ksatRepositoryRepo.update(intentDTO.getPks(), update);
        }
        else if((intentDTO = testRepo.findOne(11111, field, doc)) != null){
            testRepo.update(intentDTO.getPks(), update);
        }

        log.info("Update " + intentDTO.getElseData() + " -> " + update);
    }
}
