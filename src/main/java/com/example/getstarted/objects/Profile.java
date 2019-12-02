package com.example.getstarted.objects;

import java.util.Date;
import java.util.Objects;

/**
 * Profile Class
 */
public class Profile {
  // [START user]
    private Long id;
    private String first;
    private String last;
    private String title;
    private String introduction;
    private String status;
    private String email;
    private String description;
    private String createdBy;
    private String createdById;
    private Date publishedDate;
    private String imageUrl;
    private String profileBg;

  // [END user]
  // [START keys]
    public static final String ID = "id";
    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final String TITLE = "title";
    public static final String INTRODUCTION = "introduction";
    public static final String STATUS = "status";
    public static final String EMAIL = "email";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_BY = "createdBy";
    public static final String CREATED_BY_ID = "createdById";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PROFILE_BG = "profileBg";
    // [END keys]

    // [START constructor]
    // We use a Builder pattern here to simplify and standardize construction of Profile objects.
    private Profile(Builder builder) {
        this.id = builder.id;
        this.first = builder.first;
        this.last = builder.last;
        this.title = builder.title;
        this.introduction = builder.introduction;
        this.status = builder.status;
        this.email = builder.email;
        this.description = builder.description;
        this.createdBy = builder.createdBy;
        this.createdById = builder.createdById;
        this.publishedDate = builder.publishedDate;
        this.imageUrl = builder.imageUrl;
    }
    // [END constructor]

    // [START builder]
    public static class Builder {
        private Long id;
        private String first;
        private String last;
        private String title;
        private String introduction;
        private String status;
        private String email;
        private String description;
        private String createdBy;
        private String createdById;
        private Date publishedDate;
        private String imageUrl;

        /**
            * Build constructor
            * @param id id
            * @return Builder
        */
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder first(String first) {
            this.first = first;
            return this;
        }

        public Builder last(String last) {
            this.last = last;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
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

        public Builder publishedDate(Date publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Profile build() {
            return new Profile(this);
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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
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
    * Customized toString() with instance variables
    * @return
    */
    @Override
    public String toString() {
        return "Profile{" +
        "id=" + id +
        ", first='" + first + '\'' +
        ", last='" + last + '\'' +
        ", title='" + title + '\'' +
        ", introduction='" + introduction + '\'' +
        ", status='" + status + '\'' +
        ", email='" + email + '\'' +
        ", description='" + description + '\'' +
        ", createdBy='" + createdBy + '\'' +
        ", createdById='" + createdById + '\'' +
        ", publishedDate='" + publishedDate + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        '}';
    }

    /**
    * Customized equals() with instance variables
    * @param o
    * @return
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) &&
        Objects.equals(first, profile.first) &&
        Objects.equals(last, profile.last) &&
        Objects.equals(title, profile.title) &&
        Objects.equals(introduction, profile.introduction) &&
        Objects.equals(status, profile.status) &&
        Objects.equals(email, profile.email) &&
        Objects.equals(description, profile.description) &&
        Objects.equals(createdBy, profile.createdBy) &&
        Objects.equals(createdById, profile.createdById) &&
        Objects.equals(publishedDate, profile.publishedDate) &&
        Objects.equals(imageUrl, profile.imageUrl);
    }
}