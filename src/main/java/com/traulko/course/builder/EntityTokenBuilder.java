package com.traulko.course.builder;

import com.traulko.course.entity.EntityToken;

public class EntityTokenBuilder {
    private Integer tokenId;
    private String tokenUuid;

    /**
     * Sets token id.
     *
     * @param tokenId the token id
     */
    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * Sets token uuid.
     *
     * @param tokenUuid the token uuid
     */
    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public EntityToken getToken() {
        return new EntityToken(tokenId, tokenUuid);
    }
}
