/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.objects;

// [START example]

import java.util.Objects;

/**
 * defining an entity, PersonGroupAssoc, w
 * hich is "relational" in the sense that it maps a personId with a groupId.
 */
public class Association {
    // [START group]
    private Long id;
    private Long personId;
    private Long groupId;

    // [END group]
    // [START keys]
    public static final String ID = "id";
    public static final String PERSON_ID = "personId";
    public static final String GROUP_ID = "groupId";
    // [END keys]

    // [START constructor]
    // We use a Builder pattern here to simplify and standardize construction of Group objects.
    private Association(Builder builder) {
        this.id = builder.id;
        this.personId = builder.personId;
        this.groupId = builder.groupId;
    }
    // [END constructor]

    // [START builder]
    public static class Builder {
        private Long id;
        private Long personId;
        private Long groupId;

        /**
         * Build constructor
         * @param id id
         * @return Builder
         */
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder personId(Long personId) {
            this.personId = personId;
            return this;
        }

        public Builder groupId(Long groupId) {
            this.groupId = groupId;
            return this;
        }

        public Association build() {
            return new Association(this);
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
        return "Association{" +
                "id=" + id +
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
        Association that = (Association) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(groupId, that.groupId);
    }
}
// [END example]
