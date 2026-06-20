package com.csms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "system_info")
public class SystemInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meta_field", nullable = false, columnDefinition = "TEXT")
    private String metaField;

    @Column(name = "meta_value", nullable = false, columnDefinition = "TEXT")
    private String metaValue;

    public SystemInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetaField() {
        return metaField;
    }

    public void setMetaField(String metaField) {
        this.metaField = metaField;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }
}
