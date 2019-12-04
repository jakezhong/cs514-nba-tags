package com.example.getstarted.objects;

import java.util.Date;
import java.util.Objects;

/**
 * Person class with different fields
 */
public class Person {
    // [START person]
    private Long id;
    private String first;
    private String last;
    private String title;
    private String introduction;
    private String email;
    private String phone;
    private String address;
    private String category;
    private String status;
    private String description;
    private String createdBy;
    private String createdById;
    private Date publishedDate;
    private String imageUrl;
    private String profileBg;
    private String socialLink;
    // [END person]

    // [START keys]
    public static final String ID = "id";
    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final String TITLE = "title";
    public static final String INTRODUCTION = "introduction";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String CATEGORY = "category";
    public static final String STATUS = "status";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_BY = "createdBy";
    public static final String CREATED_BY_ID = "createdById";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PROFILE_BG = "profileBg";
    public static final String SOCIAL_LINK ="socialLink";
    // [END keys]

    // [START constructor]
    // We use a Builder pattern here to simplify and standardize construction of Person objects.
    private Person(Builder builder) {
        this.id = builder.id;
        this.first = builder.first;
        this.last = builder.last;
        this.title = builder.title;
        this.introduction = builder.introduction;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.category = builder.category;
        this.status = builder.status;
        this.description = builder.description;
        this.createdBy = builder.createdBy;
        this.publishedDate = builder.publishedDate;
        this.createdById = builder.createdById;
        this.imageUrl = builder.imageUrl;
        this.socialLink =builder.socialLink;
    }
    // [END constructor]

    // [START builder]
    public static class Builder {
        private Long id;
        private String first;
        private String last;
        private String title;
        private String introduction;
        private String email;
        private String phone;
        private String address;
        private String category;
        private String status;
        private String description;
        private String createdBy;
        private String createdById;
        private Date publishedDate;
        private String imageUrl;
        private String socialLink;

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

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
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

        public Builder publishedDate(Date publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder socialLink(String socialLink) {
            this.socialLink = socialLink;
            return this;
        }

        public Person build() {
            return new Person(this);
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

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(String socialLink) {
        this.socialLink = socialLink;
    }
    // [END builder]

    /**
    * Customized toString() with instance variables
    * @return
    */
    @Override
    public String toString() {
    return "Person{" +
            "id=" + id +
            ", first='" + first + '\'' +
            ", last='" + last + '\'' +
            ", title='" + title + '\'' +
            ", introduction='" + introduction + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", category='" + category + '\'' +
            ", status='" + status + '\'' +
            ", description='" + description + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdById='" + createdById + '\'' +
            ", publishedDate='" + publishedDate + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", socialLink='" + socialLink + '\'' +
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
    Person person = (Person) o;
    return Objects.equals(id, person.id) &&
            Objects.equals(first, person.first) &&
            Objects.equals(last, person.last) &&
            Objects.equals(title, person.title) &&
            Objects.equals(introduction, person.introduction) &&
            Objects.equals(email, person.email) &&
            Objects.equals(phone, person.phone) &&
            Objects.equals(address, person.address) &&
            Objects.equals(category, person.category) &&
            Objects.equals(status, person.status) &&
            Objects.equals(description, person.description) &&
            Objects.equals(createdBy, person.createdBy) &&
            Objects.equals(createdById, person.createdById) &&
            Objects.equals(publishedDate, person.publishedDate) &&
            Objects.equals(imageUrl, person.imageUrl) &&
            Objects.equals(socialLink, person.socialLink);
    }
}