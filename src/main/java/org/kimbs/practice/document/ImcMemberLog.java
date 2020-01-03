package org.kimbs.practice.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "ImcMemberLog")
@Data
@CompoundIndex(def = "{'username':1, 'appUserId':-1}", name = "compound_index")
public class ImcMemberLog {

    @MongoId
    private ObjectId id;

    private String username;

    private String appUserId;

    @Field("EMAIL")
    private String email;

    private int score;

    private LocalDateTime timestamp;
}