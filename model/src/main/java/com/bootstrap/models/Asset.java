package com.bootstrap.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Asset table maps generic commentable and reviewable assets to class hierarchy.
 */
@Entity
public class Asset {

    @Id
    @GeneratedValue
    private long assetId;

    @Column
    private String className;

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
