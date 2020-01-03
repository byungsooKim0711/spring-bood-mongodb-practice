package org.kimbs.practice.repository;

import org.bson.types.ObjectId;
import org.kimbs.practice.document.ImcMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImcMemberRepository extends MongoRepository<ImcMember, Long> {

    @Query(value = "{'score': {$lte : ?0, $gte : ?1}}", /*fields = "{_id: 0, username: 1}",*/ delete = true)
    List<ImcMember> asdfasdfasdfasdf(int max, int min);

    List<ImcMember> findImcMemberByUsername(String name);
}
