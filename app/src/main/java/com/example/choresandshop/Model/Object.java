package com.example.choresandshop.Model;

import java.util.Date;
import java.util.Map;

public class Object {
        private ObjectId objectId;
        private String type;
        private String alias;
        private Location location;
        private Boolean active;
        private Date creationTimestamp;
        private CreatedBy createdBy;
        private Map<String, java.lang.Object> objectDetails;


        public Object() {
        }

        public ObjectId getObjectId() {
            return objectId;
        }

        public void setObjectId(ObjectId objectId) {
            this.objectId = objectId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public Date getCreationTimestamp() {
            return creationTimestamp;
        }

        public void setCreationTimestamp(Date timestamp) {
            //        String date = new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
            this.creationTimestamp = timestamp;
        }

        public CreatedBy getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(CreatedBy createdBy) {
            this.createdBy = createdBy;
        }

    public Map<String, java.lang.Object> getObjectDetails() {
        return objectDetails;
    }

    public Object setObjectDetails(Map<String, java.lang.Object> objectDetails) {
        this.objectDetails = objectDetails;
        return this;
    }

    @Override
        public String toString() {
            return "ObjectBoundary [objectId=" + objectId + ", type=" + type + ", alias=" + alias + ", location=" + location
                    + ", active=" + active + ", timestamp=" + creationTimestamp + ", createdBy=" + createdBy + ", objectDetails="
                    + objectDetails + "]";
        }

    }
