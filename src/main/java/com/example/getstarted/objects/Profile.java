package com.example.getstarted.objects;

import java.util.Date;
import java.util.List;
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
    private String email;
    private String phone;
    private String address;
    private String linkedin;
    private String facebook;
    private String twitter;
    private String instagram;
    private String youtube;
    private String website;
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
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String LINKEDIN = "linkedin";
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String INSTAGRAM = "instagram";
    public static final String YOUTUBE = "youtube";
    public static final String WEBSITE = "website";
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
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.linkedin = builder.linkedin;
        this.facebook = builder.facebook;
        this.twitter = builder.twitter;
        this.instagram = builder.instagram;
        this.youtube = builder.youtube;
        this.website = builder.website;
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
        private String email;
        private String phone;
        private String address;
        private String linkedin;
        private String facebook;
        private String twitter;
        private String instagram;
        private String youtube;
        private String website;
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

        public Builder linkedin(String linkedin) {
            this.linkedin = linkedin;
            return this;
        }

        public Builder facebook(String facebook) {
            this.facebook = facebook;
            return this;
        }

        public Builder twitter(String twitter) {
            this.twitter = twitter;
            return this;
        }

        public Builder instagram(String instagram) {
            this.instagram = instagram;
            return this;
        }

        public Builder youtube(String youtube) {
            this.youtube = youtube;
            return this;
        }

        public Builder website(String website) {
            this.website = website;
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

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        ", linkedin='" + linkedin + '\'' +
        ", facebook='" + facebook + '\'' +
        ", twitter='" + twitter + '\'' +
        ", instagram='" + instagram + '\'' +
        ", youtube='" + youtube + '\'' +
        ", website='" + website + '\'' +
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
        Objects.equals(email, profile.email) &&
        Objects.equals(phone, profile.phone) &&
        Objects.equals(address, profile.address) &&
        Objects.equals(linkedin, profile.linkedin) &&
        Objects.equals(facebook, profile.facebook) &&
        Objects.equals(twitter, profile.twitter) &&
        Objects.equals(instagram, profile.instagram) &&
        Objects.equals(youtube, profile.youtube) &&
        Objects.equals(website, profile.website) &&
        Objects.equals(description, profile.description) &&
        Objects.equals(createdBy, profile.createdBy) &&
        Objects.equals(createdById, profile.createdById) &&
        Objects.equals(publishedDate, profile.publishedDate) &&
        Objects.equals(imageUrl, profile.imageUrl);
    }
}