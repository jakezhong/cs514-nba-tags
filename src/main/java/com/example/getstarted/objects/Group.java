package com.example.getstarted.objects;

import java.util.Date;
import java.util.Objects;

// [START example]
public class Group {
    // [START person]
    private Long id;
    private String name;
    private String introduction;
    private String category;
    private String type;
    private String linkedin;
    private String facebook;
    private String twitter;
    private String instagram;
    private String youtube;
    private String website;
    private String description;
    private String createdBy;
    private String createdById;
    private Date createdDate;
    private String imageUrl;
    private String profileBg;

    // [END person]
    // [START keys]
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INTRODUCTION = "introduction";
    public static final String CATEGORY = "category";
    public static final String TYPE = "type";
    public static final String LINKEDIN = "linkedin";
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String INSTAGRAM = "instagram";
    public static final String YOUTUBE = "youtube";
    public static final String WEBSITE = "website";
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
        this.type = builder.type;
        this.linkedin = builder.linkedin;
        this.facebook = builder.facebook;
        this.twitter = builder.twitter;
        this.instagram = builder.instagram;
        this.youtube = builder.youtube;
        this.website = builder.website;
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
        private String type;
        private String linkedin;
        private String facebook;
        private String twitter;
        private String instagram;
        private String youtube;
        private String website;
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

        public Builder type(String type) {
            this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                Objects.equals(type, group.type) &&
                Objects.equals(linkedin, group.linkedin) &&
                Objects.equals(facebook, group.facebook) &&
                Objects.equals(twitter, group.twitter) &&
                Objects.equals(instagram, group.instagram) &&
                Objects.equals(youtube, group.youtube) &&
                Objects.equals(website, group.website) &&
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
                ", type='" + type + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", instagram='" + instagram + '\'' +
                ", youtube='" + youtube + '\'' +
                ", website='" + website + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdById='" + createdById + '\'' +
                ", createdDate=" + createdDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", profileBg='" + profileBg + '\'' +
                '}';
    }
}
// [END example]
