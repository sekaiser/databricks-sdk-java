// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.

package com.databricks.sdk.service.unitycatalog;

import java.util.Map;

import com.databricks.sdk.annotation.QueryParam;
import com.fasterxml.jackson.annotation.JsonProperty;



public class CreateTableConstraint {
    /**
     * A table constraint, as defined by *one* of the following fields being
     * set: __primary_key_constraint__, __foreign_key_constraint__,
     * __named_table_constraint__.
     */
    @JsonProperty("constraint")
    private TableConstraint constraint;
    
    /**
     * The full name of the table referenced by the constraint.
     */
    @JsonProperty("full_name_arg")
    private String fullNameArg;
    
    public CreateTableConstraint setConstraint(TableConstraint constraint) {
        this.constraint = constraint;
        return this;
    }

    public TableConstraint getConstraint() {
        return constraint;
    }
    
    public CreateTableConstraint setFullNameArg(String fullNameArg) {
        this.fullNameArg = fullNameArg;
        return this;
    }

    public String getFullNameArg() {
        return fullNameArg;
    }
    
}