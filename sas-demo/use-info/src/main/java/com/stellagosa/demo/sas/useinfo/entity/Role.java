package com.stellagosa.demo.sas.useinfo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Stellagosa
 * @Date: 2022/6/23 10:47
 * @Description:
 */
public class Role {

    private Long id;
    private String code;
    private String name;
    private Boolean deleted;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(code, role.code) && Objects.equals(name, role.name) && Objects.equals(deleted, role.deleted) && Objects.equals(description, role.description) && Objects.equals(createdTime, role.createdTime) && Objects.equals(updatedTime, role.updatedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, deleted, description, createdTime, updatedTime);
    }
}
