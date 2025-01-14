// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.

package com.databricks.sdk.service.catalog;

import com.databricks.sdk.support.Generated;
import com.databricks.sdk.support.ToStringer;
import java.util.Objects;

/** Get a Registered Model */
@Generated
public class GetRegisteredModelRequest {
  /** The three-level (fully qualified) name of the registered model */
  private String fullName;

  public GetRegisteredModelRequest setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GetRegisteredModelRequest that = (GetRegisteredModelRequest) o;
    return Objects.equals(fullName, that.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullName);
  }

  @Override
  public String toString() {
    return new ToStringer(GetRegisteredModelRequest.class).add("fullName", fullName).toString();
  }
}
