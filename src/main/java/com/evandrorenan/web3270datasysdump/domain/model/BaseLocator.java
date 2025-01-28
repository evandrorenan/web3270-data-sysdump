package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Base Locator in the 3270 terminal system.
 * Base Locators are data structures that contain information about program storage areas
 * and their locations in memory at the time of an abnormal termination (Abend).
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Builder
@Document(collection = "sysdumps")
public record BaseLocator(
    /**
     * The unique identifier of the base locator.
     */
    ObjectId _id,
    /**
     * The address in memory where this base locator starts.
     */
    String address,
    /**
     * The hexadecimal content of the storage area referenced by this base locator.
     */
    String hexContent
) {}
