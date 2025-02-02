// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.

package com.databricks.sdk.service.catalog;

import com.databricks.sdk.support.Generated;
import com.databricks.sdk.support.ToStringer;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@Generated
public class UpdateRegisteredModelRequest {
  /** The comment attached to the registered model */
  @JsonProperty("comment")
  private String comment;

  /** The three-level (fully qualified) name of the registered model */
  private String fullName;

  /** The name of the registered model */
  @JsonProperty("name")
  private String name;

  /** The identifier of the user who owns the registered model */
  @JsonProperty("owner")
  private String owner;

  public UpdateRegisteredModelRequest setComment(String comment) {
    this.comment = comment;
    return this;
  }

  public String getComment() {
    return comment;
  }

  public UpdateRegisteredModelRequest setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  public UpdateRegisteredModelRequest setName(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateRegisteredModelRequest setOwner(String owner) {
    this.owner = owner;
    return this;
  }

  public String getOwner() {
    return owner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateRegisteredModelRequest that = (UpdateRegisteredModelRequest) o;
    return Objects.equals(comment, that.comment)
        && Objects.equals(fullName, that.fullName)
        && Objects.equals(name, that.name)
        && Objects.equals(owner, that.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, fullName, name, owner);
  }

  @Override
  public String toString() {
    return new ToStringer(UpdateRegisteredModelRequest.class)
        .add("comment", comment)
        .add("fullName", fullName)
        .add("name", name)
        .add("owner", owner)
        .toString();
  }
}
