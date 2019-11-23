package com.example.getstarted.objects;

import java.util.Objects;

public class PostTag {
    // [START group]
    private Long id;
    private Long postId;
    private Long personId;
    private Long groupId;
    // [END group]

    // [START keys]
    public static final String ID = "id";
    public static final String POST_ID = "postId";
    public static final String PERSON_ID = "personId";
    public static final String GROUP_ID = "groupId";
    // [END keys]

    // [START constructor]
    // We use a Builder pattern here to simplify and standardize construction of Group objects.
    private PostTag(PostTag.Builder builder) {
        this.id = builder.id;
        this.postId = builder.postId;
        this.personId = builder.personId;
        this.groupId = builder.groupId;
    }
    // [END constructor]

    // [START builder]
    public static class Builder {
        private Long id;
        private Long postId;
        private Long personId;
        private Long groupId;

        /**
         * Build constructor
         * @param id id
         * @return Builder
         */
        public PostTag.Builder id(Long id) {
            this.id = id;
            return this;
        }

        public PostTag.Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public PostTag.Builder personId(Long personId) {
            this.personId = personId;
            return this;
        }

        public PostTag.Builder groupId(Long groupId) {
            this.groupId = groupId;
            return this;
        }

        public PostTag build() {
            return new PostTag(this);
        }
    }

    /**
     * get method
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * set method to update id
     * @return id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get method to get postId
     * @return id
     */
    public Long getPostId() {
        return postId;
    }
    /**
     * set method to update postId
     * @return id
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * get method to get personId
     * @return id
     */
    public Long getPersonId() {
        return personId;
    }
    /**
     * set method to update personId
     * @return id
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * get method to get groupId
     * @return id
     */
    public Long getGroupId() {
        return groupId;
    }
    /**
     * set method to update groupId
     * @return id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    // [END builder]

    /**
     * Override toString method
     * @return String
     */
    @Override
    public String toString() {
        return "PostTag{" +
                "id=" + id +
                ", postId=" + postId +
                ", personId=" + personId +
                ", groupId=" + groupId +
                '}';
    }

    /**
     * Override equals method
     * @return String
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTag that = (PostTag) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(postId, that.postId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(groupId, that.groupId);
    }
}
