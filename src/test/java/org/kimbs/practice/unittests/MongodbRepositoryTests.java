package org.kimbs.practice.unittests;

import org.junit.jupiter.api.Test;
import org.kimbs.practice.document.ImcMember;
import org.kimbs.practice.repository.ImcMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class MongodbRepositoryTests {

    @Autowired
    private ImcMemberRepository imcMemberRepository;

    @Test
    void test1() {
        ImcMember imcMember1 = new ImcMember();
        imcMember1.setAppUserId("kbs");
        imcMember1.setTimestamp(LocalDateTime.now());
        imcMember1.setUsername("kbs");
        imcMember1.setEmail("kbs@kbs.kbs");
        imcMember1.setScore(40);

        ImcMember inserted = imcMemberRepository.insert(imcMember1);

        inserted.setUsername("@@@@@@@@@@@@@@@@@@@@@@@");
        ImcMember updated = imcMemberRepository.save(inserted);
        System.out.println(updated);
    }

    @Test
    void test() {
        ImcMember imcMember1 = new ImcMember();
        imcMember1.setAppUserId("kbs");
        imcMember1.setTimestamp(LocalDateTime.now());
        imcMember1.setUsername("kbs");
        imcMember1.setEmail("kbs@kbs.kbs");
        imcMember1.setScore(40);

        ImcMember imcMember2 = new ImcMember();
        imcMember2.setAppUserId("lee");
        imcMember2.setTimestamp(LocalDateTime.now());
        imcMember2.setUsername("lee");
        imcMember2.setEmail("lee@lee.lee");
        imcMember2.setScore(50);

        ImcMember imcMember3 = new ImcMember();
        imcMember3.setAppUserId("park");
        imcMember3.setTimestamp(LocalDateTime.now());
        imcMember3.setUsername("park");
        imcMember3.setEmail("park@a,.park");
        imcMember3.setScore(100);

        imcMemberRepository.insert(Arrays.asList(imcMember1, imcMember2, imcMember3));
        List<ImcMember> members = imcMemberRepository.findImcMemberByUsername("kbs");

        System.out.println(members);
    }

    @Test
    void testCustomQuery() {
        ImcMember imcMember1 = new ImcMember();
        imcMember1.setAppUserId("kbs");
        imcMember1.setTimestamp(LocalDateTime.now());
        imcMember1.setUsername("kbs");
        imcMember1.setEmail("kbs@kbs.kbs");
        imcMember1.setScore(40);

        ImcMember imcMember2 = new ImcMember();
        imcMember2.setAppUserId("lee");
        imcMember2.setTimestamp(LocalDateTime.now());
        imcMember2.setUsername("lee");
        imcMember2.setEmail("lee@lee.lee");
        imcMember2.setScore(50);

        ImcMember imcMember3 = new ImcMember();
        imcMember3.setAppUserId("park");
        imcMember3.setTimestamp(LocalDateTime.now());
        imcMember3.setUsername("park");
        imcMember3.setEmail("park@park.park");
        imcMember3.setScore(100);

        imcMemberRepository.insert(Arrays.asList(imcMember1, imcMember2, imcMember3));

        List<ImcMember> members = imcMemberRepository.asdfasdfasdfasdf(100, 40);
        System.out.println(members);
    }

    @Test
    void testInsertSpeedWithMongodbRepository() {
        long startTime = System.nanoTime();
        List<ImcMember> members = new ArrayList<>();

        for (int i=0; i<1000000; i++) {
            ImcMember imcMember = new ImcMember();
            imcMember.setUsername("kbs");
            imcMember.setEmail("kbs@humuson.com");
//            imcMember.setId(UUID.randomUUID().toString());
            imcMember.setTimestamp(LocalDateTime.now());
            members.add(imcMember);
        }

//        long startTime = System.nanoTime();
        // saveAll 6.8분
        // insert 57초 (insert 만 따지고 보면 51초 정도)
        imcMemberRepository.insert(members);

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

//        List<ImcMember> actual = mongoTemplate.findAllAndRemove(new Query(), ImcMember.class);

//        assertThat(actual).hasSize(100000);
    }
}
