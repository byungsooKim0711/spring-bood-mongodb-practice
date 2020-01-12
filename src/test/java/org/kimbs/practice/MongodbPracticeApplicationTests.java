package org.kimbs.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kimbs.practice.document.ImcMember;
import org.kimbs.practice.service.ImcMemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
class MongodbPracticeApplicationTests {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
    ImcMemberServiceImpl imcMemberService;

	@BeforeEach
	void init() {
		ImcMember imcMember = new ImcMember();
		imcMember.setUsername("kimbs");
		imcMember.setScore(100);
		imcMember.setAppUserId("app");
		imcMember.setTimestamp(LocalDateTime.now());
		imcMember.setEmail("kbs@kbs.kbs");

		mongoTemplate.insert(imcMember);
	}

	@Test
	void testTransaction() {
        imcMemberService.updateImcMember();
	}
}
