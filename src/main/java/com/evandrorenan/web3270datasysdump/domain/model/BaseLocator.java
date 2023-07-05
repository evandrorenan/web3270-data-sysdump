package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "sysdumps")
public record BaseLocator(
    ObjectId _id,
    String address,
    String hexContent
) {}
