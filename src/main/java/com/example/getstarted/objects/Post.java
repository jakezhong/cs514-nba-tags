package com.example.getstarted.objects;

import java.util.Date;
import java.util.Objects;

public class Post {
    // [START post]
    private Long id;
    private String slug;
    private String title;
    private String introduction;
    private String category;
    private String type;
    private String status;
    private String personTag;
    private String groupTag;
    private String description;
    private String createdBy;
    private String createdById;
    private Date publishedDate;
    private String imageUrl;
    private String profileBg;
    // [END post]

    // [START keys]
    public static final String ID = "id";
    public static final String SLUG = "slug";
    public static final String TITLE = "title";
    public static final String INTRODUCTION = "introduction";
    public static final String CATEGORY = "category";
    public static final String TYPE = "type";
    public static final String STATUS = "type";
    public static final String PERSON_TAG = "personTag";
    public static final String GROUP_TAG = "groupTag";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_BY = "createdBy";
    public static final String CREATED_BY_ID = "createdById";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PROFILE_BG = "profileBg";
  // [END keys]

  // [START constructor]
  // We use a Builder pattern here to simplify and standardize construction of Post objects.
  private Post(Builder builder) {
      this.id = builder.id;
      this.slug = builder.slug;
      this.title = builder.title;
      this.introduction = builder.introduction;
      this.category = builder.category;
      this.type = builder.type;
      this.status = builder.status;
      this.personTag = builder.personTag;
      this.groupTag = builder.groupTag;
      this.description = builder.description;
      this.createdBy = builder.createdBy;
      this.publishedDate = builder.publishedDate;
      this.createdById = builder.createdById;
      this.imageUrl = builder.imageUrl;
  }
  // [END constructor]

  // [START builder]
  public static class Builder {
      private Long id;
      private String slug;
      private String title;
      private String introduction;
      private String category;
      private String type;
      private String status;
      private String personTag;
      private String groupTag;
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

      public Builder slug(String slug) {
          this.slug = slug;
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

      public Builder category(String category) {
          this.category = category;
          return this;
      }

      public Builder type(String type) {
          this.type = type;
          return this;
      }

      public Builder status(String status) {
          this.status = status;
          return this;
      }

      public Builder personTag(String personTag) {
          this.personTag = personTag;
          return this;
      }

      public Builder groupTag(String groupTag) {
          this.groupTag = groupTag;
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

      public Post build() {
      return new Post(this);
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPersonTag() {
        return personTag;
    }

    public void setPersonTag(String personTag) {
        this.personTag = personTag;
    }
    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
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
        return "Post{" +
            "id=" + id +
            ", slug='" + slug + '\'' +
            ", title='" + title + '\'' +
            ", introduction='" + introduction + '\'' +
            ", category='" + category + '\'' +
            ", type='" + type + '\'' +
            ", status='" + status + '\'' +
            ", personTag='" + personTag + '\'' +
            ", groupTag='" + groupTag + '\'' +
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
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(slug, post.slug) &&
                Objects.equals(title, post.title) &&
                Objects.equals(introduction, post.introduction) &&
                Objects.equals(category, post.category) &&
                Objects.equals(type, post.type) &&
                Objects.equals(status, post.status) &&
                Objects.equals(personTag, post.personTag) &&
                Objects.equals(groupTag, post.groupTag) &&
                Objects.equals(description, post.description) &&
                Objects.equals(createdBy, post.createdBy) &&
                Objects.equals(createdById, post.createdById) &&
                Objects.equals(publishedDate, post.publishedDate) &&
                Objects.equals(imageUrl, post.imageUrl);
    }
}