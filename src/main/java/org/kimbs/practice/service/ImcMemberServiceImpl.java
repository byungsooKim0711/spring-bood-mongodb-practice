package org.kimbs.practice.service;

import org.kimbs.practice.document.ImcMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ImcMemberServiceImpl extends ImcMemberService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ImcMember updateImcMember() {
        ImcMember imcMember = mongoTemplate.findOne(new Query().limit(1), ImcMember.class);

        imcMember.setAppUserId("@@@@@@@@@@@@");
        imcMember.setScore(0);
        imcMember.setUsername("null");

        ImcMember savedMember = mongoTemplate.save(imcMember);

        if (1 == 1) {
            int i = 1/0;
        }

        return savedMember;
    }
}
