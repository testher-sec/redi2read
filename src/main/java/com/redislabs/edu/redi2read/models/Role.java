package com.redislabs.edu.redi2read.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Builder;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash
@Builder
public class Role {

    @Id
    private String id;

    @Indexed
    private String name;
}
