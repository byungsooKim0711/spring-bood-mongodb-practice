package org.kimbs.practice.repository;

import org.kimbs.practice.document.ImcMemberLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImcMemberLogRepository extends MongoRepository<ImcMemberLog, String> {
}
