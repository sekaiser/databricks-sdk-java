// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.
package com.databricks.sdk.service.billing;

import com.databricks.sdk.core.ApiClient;
import com.databricks.sdk.support.Generated;
import java.util.HashMap;
import java.util.Map;

/** Package-local implementation of BillableUsage */
@Generated
class BillableUsageImpl implements BillableUsageService {
  private final ApiClient apiClient;

  public BillableUsageImpl(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public void download(DownloadRequest request) {
    String path =
        String.format("/api/2.0/accounts/%s/usage/download", apiClient.configuredAccountID());
    Map<String, String> headers = new HashMap<>();
    apiClient.GET(path, request, Void.class, headers);
  }
}
