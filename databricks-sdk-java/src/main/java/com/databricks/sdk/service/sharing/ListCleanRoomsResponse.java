// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.

package com.databricks.sdk.service.sharing;

import com.databricks.sdk.support.Generated;
import com.databricks.sdk.support.ToStringer;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Objects;

@Generated
public class ListCleanRoomsResponse {
  /** An array of clean rooms. Remote details (central) are not included. */
  @JsonProperty("clean_rooms")
  private Collection<CleanRoomInfo> cleanRooms;

  public ListCleanRoomsResponse setCleanRooms(Collection<CleanRoomInfo> cleanRooms) {
    this.cleanRooms = cleanRooms;
    return this;
  }

  public Collection<CleanRoomInfo> getCleanRooms() {
    return cleanRooms;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ListCleanRoomsResponse that = (ListCleanRoomsResponse) o;
    return Objects.equals(cleanRooms, that.cleanRooms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cleanRooms);
  }

  @Override
  public String toString() {
    return new ToStringer(ListCleanRoomsResponse.class).add("cleanRooms", cleanRooms).toString();
  }
}