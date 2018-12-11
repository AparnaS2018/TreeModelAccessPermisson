package com.sampleProject.dataobject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aparnasalve on 13/9/17.
 */

public class PermissionTreeModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Child {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("parent_id")
        @Expose
        private String parentId;
        @SerializedName("module_name")
        @Expose
        private String moduleName;
        @SerializedName("is_last_node")
        @Expose
        private Boolean isLastNode;
        @SerializedName("can_access")
        @Expose
        private String canAccess;
        @SerializedName("child")
        @Expose
        private List<Child_> child = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public Boolean getIsLastNode() {
            return isLastNode;
        }

        public void setIsLastNode(Boolean isLastNode) {
            this.isLastNode = isLastNode;
        }

        public String getCanAccess() {
            return canAccess;
        }

        public void setCanAccess(String canAccess) {
            this.canAccess = canAccess;
        }

        public List<Child_> getChild() {
            return child;
        }

        public void setChild(List<Child_> child) {
            this.child = child;
        }

    }

    public class Child_ {

        @SerializedName("can_write")
        @Expose
        private Boolean canWrite;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("can_read")
        @Expose
        private Boolean canRead;
        @SerializedName("can_update")
        @Expose
        private Boolean canUpdate;

        public Boolean getCanWrite() {
            return canWrite;
        }

        public void setCanWrite(Boolean canWrite) {
            this.canWrite = canWrite;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Boolean getCanRead() {
            return canRead;
        }

        public void setCanRead(Boolean canRead) {
            this.canRead = canRead;
        }

        public Boolean getCanUpdate() {
            return canUpdate;
        }

        public void setCanUpdate(Boolean canUpdate) {
            this.canUpdate = canUpdate;
        }

    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("parent_id")
        @Expose
        private String parentId;
        @SerializedName("module_name")
        @Expose
        private String moduleName;
        @SerializedName("is_last_node")
        @Expose
        private Boolean isLastNode;
        @SerializedName("can_access")
        @Expose
        private String canAccess;
        @SerializedName("child")
        @Expose
        private List<Child> child = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public Boolean getIsLastNode() {
            return isLastNode;
        }

        public void setIsLastNode(Boolean isLastNode) {
            this.isLastNode = isLastNode;
        }

        public String getCanAccess() {
            return canAccess;
        }

        public void setCanAccess(String canAccess) {
            this.canAccess = canAccess;
        }

        public List<Child> getChild() {
            return child;
        }

        public void setChild(List<Child> child) {
            this.child = child;
        }

    }


}
