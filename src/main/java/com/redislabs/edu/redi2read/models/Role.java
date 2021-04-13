package com.redislabs.edu.redi2read.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Builder;

@Data
@RedisHash
@Builder
public class Role {

    @Id
    private String id;
    private String name;
}
