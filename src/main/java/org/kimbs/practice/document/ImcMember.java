package org.kimbs.practice.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "ImcMember")
@Data
@CompoundIndex(def = "{'username':1, 'appUserId':-1}", name = "compound_index")
public class ImcMember {

    @MongoId
    private ObjectId id;

    private String username;

    private String appUserId;

    @Field("EMAIL")
    private String email;

    private int score;

    @Indexed(name = "expire_after_seconds_index", expireAfterSeconds = 3, direction = IndexDirection.DESCENDING)
    private LocalDateTime timestamp;
}