package com.traulko.course.entity;

import java.io.Serializable;

public class EntityToken implements Serializable {
    private Integer tokenId;
    private String tokenUuid;

    public EntityToken(Integer tokenId, String tokenUuid) {
        this.tokenId = tokenId;
        this.tokenUuid = tokenUuid;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }
}
