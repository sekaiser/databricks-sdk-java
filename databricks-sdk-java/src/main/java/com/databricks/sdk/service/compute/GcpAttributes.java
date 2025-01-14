// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.

package com.databricks.sdk.service.compute;

import com.databricks.sdk.support.Generated;
import com.databricks.sdk.support.ToStringer;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@Generated
public class GcpAttributes {
  /**
   * This field determines whether the instance pool will contain preemptible VMs, on-demand VMs, or
   * preemptible VMs with a fallback to on-demand VMs if the former is unavailable.
   */
  @JsonProperty("availability")
  private GcpAvailability availability;

  /** boot disk size in GB */
  @JsonProperty("boot_disk_size")
  private Long bootDiskSize;

  /**
   * If provided, the cluster will impersonate the google service account when accessing gcloud
   * services (like GCS). The google service account must have previously been added to the
   * Databricks environment by an account administrator.
   */
  @JsonProperty("google_service_account")
  private String googleServiceAccount;

  /**
   * If provided, each node (workers and driver) in the cluster will have this number of local SSDs
   * attached. Each local SSD is 375GB in size. Refer to [GCP documentation] for the supported
   * number of local SSDs for each instance type.
   *
   * <p>[GCP documentation]:
   * https://cloud.google.com/compute/docs/disks/local-ssd#choose_number_local_ssds
   */
  @JsonProperty("local_ssd_count")
  private Long localSsdCount;

  public GcpAttributes setAvailability(GcpAvailability availability) {
    this.availability = availability;
    return this;
  }

  public GcpAvailability getAvailability() {
    return availability;
  }

  public GcpAttributes setBootDiskSize(Long bootDiskSize) {
    this.bootDiskSize = bootDiskSize;
    return this;
  }

  public Long getBootDiskSize() {
    return bootDiskSize;
  }

  public GcpAttributes setGoogleServiceAccount(String googleServiceAccount) {
    this.googleServiceAccount = googleServiceAccount;
    return this;
  }

  public String getGoogleServiceAccount() {
    return googleServiceAccount;
  }

  public GcpAttributes setLocalSsdCount(Long localSsdCount) {
    this.localSsdCount = localSsdCount;
    return this;
  }

  public Long getLocalSsdCount() {
    return localSsdCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GcpAttributes that = (GcpAttributes) o;
    return Objects.equals(availability, that.availability)
        && Objects.equals(bootDiskSize, that.bootDiskSize)
        && Objects.equals(googleServiceAccount, that.googleServiceAccount)
        && Objects.equals(localSsdCount, that.localSsdCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availability, bootDiskSize, googleServiceAccount, localSsdCount);
  }

  @Override
  public String toString() {
    return new ToStringer(GcpAttributes.class)
        .add("availability", availability)
        .add("bootDiskSize", bootDiskSize)
        .add("googleServiceAccount", googleServiceAccount)
        .add("localSsdCount", localSsdCount)
        .toString();
  }
}
