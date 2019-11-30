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

/**
 * To parameter(use generic type) List & curor
 * @param <K>
 */
public class SocialLink<K> {

    private String socialLinkName;
    private String socialLinkUrl;
    private Long personId;
    private Long id;

    public static final String SOCIALLINKNAME = "socialName";
    public static final String SOCIALLINKURL = "socialURl";
    public static final String  SOCIALLINKID = "socialID";
    public static final String  PERSONID = "personId";

    /**
     * Constructor
     * @param
     * @param
     * @param
     */
    public SocialLink(Builder builder) {
        this.socialLinkUrl = builder.socialLinkUrl;
        this.socialLinkName = builder.socialLinkName;
        this.personId = builder.personId;
        this.id = builder.id;
    }


    public static final class Builder {
        private String socialLinkName;
        private String socialLinkUrl;
        private Long personId;
        private Long id;

        public Builder() {
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder socialLinkName(String socialLinkName) {
            this.socialLinkName = socialLinkName;
            return this;
        }

        public Builder socialLinkUrl(String socialLinkUrl) {
            this.socialLinkUrl = socialLinkUrl;
            return this;
        }
        public Builder personId(Long personId){
            this.personId = personId;
            return  this;
        }

        public SocialLink build() {
            return new SocialLink(this);
        }
    }


    public String getSocialLinkName() {
        return socialLinkName;
    }

    public void setSocialLinkName(String socialLinkName) {
        this.socialLinkName = socialLinkName;
    }

    public String getSocialLinkUrl() {
        return socialLinkUrl;
    }

    public void setSocialLinkUrl(String socialLinkUrl) {
        this.socialLinkUrl = socialLinkUrl;
    }
    public Long getPersonId(){
        return personId;
    }

    public void setPersonId(Long personId){
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
// [END example]
