package org.kimbs.practice.unittests;

import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kimbs.practice.document.ImcMember;
import org.kimbs.practice.repository.ImcMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MongodbTemplateTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ImcMemberRepository imcMemberRepository;

    @Test
    void testBulkInsertWithMongoTemplate() {
        ImcMember imcMember1 = new ImcMember();
        imcMember1.setUsername("kjh");
        imcMember1.setEmail("kjh@kjh.kjh");
        imcMember1.setTimestamp(LocalDateTime.now());

        ImcMember imcMember2 = new ImcMember();
        imcMember2.setUsername("kbc");
        imcMember2.setEmail("kbc@kbc.kbc");
        imcMember2.setTimestamp(LocalDateTime.now());

        ImcMember imcMember3 = new ImcMember();
        imcMember3.setUsername("jhj");
        imcMember3.setEmail("jhj@jhj.jhj");
        imcMember3.setTimestamp(LocalDateTime.now());

        ImcMember imcMember4 = new ImcMember();
        imcMember4.setUsername("kbs");
        imcMember4.setEmail("kbs@kbs.kbs");
        imcMember4.setTimestamp(LocalDateTime.now());

        List<ImcMember> members = Arrays.asList(imcMember1, imcMember2, imcMember3, imcMember4);

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, "ImcMember");
        bulkOperations.insert(members);
        bulkOperations.execute();

        List<ImcMember> actual = mongoTemplate.findAll(ImcMember.class);

        assertThat(actual).hasSize(4).contains(imcMember1, imcMember4, imcMember3, imcMember2);
    }

    @Test
    void testBulkInsertWithMongoTemplate1() {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, "ImcMember");

        for (int i=0; i<10; i++) {
            ImcMember imcMember = new ImcMember();
            imcMember.setUsername("kbs");
            imcMember.setEmail("kbs@humuson.com");
            imcMember.setTimestamp(LocalDateTime.now());
            bulkOperations.insert(imcMember);
        }

        bulkOperations.execute();

        List<ImcMember> actual = mongoTemplate.findAllAndRemove(new Query(), ImcMember.class);

        assertThat(actual).hasSize(10);
    }

    @Test
    void testUpdateDocumentWithMongoTemplateUpsert() {
        ImcMember imcMember = new ImcMember();
        imcMember.setEmail("kbs@kbs.kbs");
        imcMember.setUsername("kbs");
        imcMember.setTimestamp(LocalDateTime.now());

        ImcMember imcMember1 = new ImcMember();
        imcMember1.setEmail("aa@aa.aa");
        imcMember1.setUsername("aa");
        imcMember1.setTimestamp(LocalDateTime.now());

        ImcMember inserted = mongoTemplate.insert(imcMember);
        mongoTemplate.insert(imcMember1);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(inserted.getId()));

        Update update = new Update();
        update.set("username", "updated!!");
        update.set("email", "updated!!@updated.updated");
        update.set("score", 9999);

        UpdateResult updateResult = mongoTemplate.upsert(query, update, ImcMember.class);

        ImcMember actual = mongoTemplate.findById(inserted.getId(), ImcMember.class);

        System.out.println(actual);

        assertEquals("updated!!", actual.getUsername());
        assertEquals("updated!!@updated.updated", actual.getEmail());
    }

    @Test
    void testUpdateDocumentWithMongoTemplateFindAndModify() {
        ImcMember imcMember = new ImcMember();
        imcMember.setEmail("kbs@kbs.kbs");
        imcMember.setUsername("kbs");
        imcMember.setTimestamp(LocalDateTime.now());

        ImcMember imcMember1 = new ImcMember();
        imcMember1.setEmail("aa@aa.aa");
        imcMember1.setUsername("aa");
        imcMember1.setTimestamp(LocalDateTime.now());

        ImcMember inserted = mongoTemplate.insert(imcMember);
        mongoTemplate.insert(imcMember1);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(inserted.getId()));

        Update update = new Update();
        update.set("username", "updated!!");
        update.set("email", "up@date.d!!");

        ImcMember updatedImcMember = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), ImcMember.class);
        assertEquals("updated!!", updatedImcMember.getUsername());
        assertEquals("up@date.d!!", updatedImcMember.getEmail());
    }

    @Test
    void testBulkInsert() {
        long startTime = System.nanoTime();
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, "ImcMember");

        for (int i=0; i<1000000; i++) {
            ImcMember imcMember = new ImcMember();
            imcMember.setUsername("kbs");
            imcMember.setEmail("kbs@humuson.com");
            imcMember.setTimestamp(LocalDateTime.now());
            bulkOperations.insert(imcMember);
        }

        bulkOperations.execute();

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

        List<ImcMember> actual = mongoTemplate.findAllAndRemove(new Query(), ImcMember.class);

        assertThat(actual).hasSize(100000);
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
        imcMember3.setEmail("park@park.park");
        imcMember3.setScore(100);

        mongoTemplate.insert(imcMember1);
        mongoTemplate.insert(imcMember2);
        mongoTemplate.insert(imcMember3);

        Query query = new Query();
        query.addCriteria(Criteria.where("score").in(100, 50).and("username").in("park"));
        List<ImcMember> members = mongoTemplate.find(query, ImcMember.class);

        System.out.println(members);
    }

    @Test
    void test1() {
        ImcMember imcMember1 = new ImcMember();
        imcMember1.setAppUserId("kbs");
        imcMember1.setTimestamp(LocalDateTime.now());
        imcMember1.setUsername("kbs");
        imcMember1.setEmail("kbs@kbs.kbs");
        imcMember1.setScore(40);

        ImcMember imcMember2 = new ImcMember();
        imcMember2.setAppUserId("123");
        imcMember2.setTimestamp(LocalDateTime.now());
        imcMember2.setUsername("123");
        imcMember2.setEmail("123@123.123");
        imcMember2.setScore(40);

        mongoTemplate.insert(imcMember1);
        mongoTemplate.insert(imcMember2);

        Query query = new Query();
        query.addCriteria(Criteria.where("score").in(40));

        List<ImcMember> moveImcMember = mongoTemplate.findAllAndRemove(query, ImcMember.class);

        mongoTemplate.insert(moveImcMember, "ImcMemberLog");
    }

    @BeforeEach
    void beforeEach() {
//        mongoTemplate.findAllAndRemove(new Query(), ImcMember.class);
    }
}
