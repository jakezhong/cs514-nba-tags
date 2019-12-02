package com.example.getstarted.objects;

import java.util.Date;
import java.util.Objects;

public class Group {
    // [START Group]
    private Long id;
    private String name;
    private String introduction;
    private String category;
    private String status;
    private String description;
    private String createdBy;
    private String createdById;
    private Date createdDate;
    private String imageUrl;
    private String profileBg;
    // [END Group]

    // [START keys]
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INTRODUCTION = "introduction";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_BY = "createdBy";
    public static final String CREATED_BY_ID = "createdById";
    public static final String CREATED_DATE = "createdDate";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PROFILE_BG = "profileBg";
    // [END keys]

    // [START constructor]
    // We use a Builder pattern here to simplify and standardize construction of Group objects.
    private Group(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduction = builder.introduction;
        this.category = builder.category;
        this.status = builder.status;
        this.description = builder.description;
        this.createdBy = builder.createdBy;
        this.createdById = builder.createdById;
        this.createdDate = builder.createdDate;
        this.imageUrl = builder.imageUrl;
        this.profileBg = builder.profileBg;
    }
    // [END constructor]

    // [START builder]
    public static class Builder {
        private Long id;
        private String name;
        private String introduction;
        private String category;
        private String status;
        private String description;
        private String createdBy;
        private String createdById;
        private Date createdDate;
        private String imageUrl;
        private String profileBg;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdById(String createdById) {
            this.createdById = createdById;
            return this;
        }

        public Builder createdDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder profileBg(String profileBg) {
            this.profileBg = profileBg;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }

    /**
     * Bunch of getters and setters
     * @return
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileBg() {
        return profileBg;
    }

    public void setProfileBg(String profileBg) {
        this.profileBg = profileBg;
    }

    // [END builder]

    /**
     * Customized equals with instance variables
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) &&
                Objects.equals(name, group.name) &&
                Objects.equals(introduction, group.introduction) &&
                Objects.equals(category, group.category) &&
                Objects.equals(status, group.status) &&
                Objects.equals(description, group.description) &&
                Objects.equals(createdBy, group.createdBy) &&
                Objects.equals(createdById, group.createdById) &&
                Objects.equals(createdDate, group.createdDate) &&
                Objects.equals(imageUrl, group.imageUrl) &&
                Objects.equals(profileBg, group.profileBg);
    }

    /**
     * Customized toString() with instance variables
     * @return
     */
    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdById='" + createdById + '\'' +
                ", createdDate=" + createdDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", profileBg='" + profileBg + '\'' +
                '}';
    }
}