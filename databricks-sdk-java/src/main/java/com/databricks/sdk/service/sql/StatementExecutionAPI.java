// Code generated from OpenAPI specs by Databricks SDK Generator. DO NOT EDIT.
package com.databricks.sdk.service.sql;

import com.databricks.sdk.core.ApiClient;
import com.databricks.sdk.support.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SQL Statement Execution API manages the execution of arbitrary SQL statements and the
 * fetching of result data.
 *
 * <p>**Release status**
 *
 * <p>This feature is in [Public Preview].
 *
 * <p>**Getting started**
 *
 * <p>We suggest beginning with the [SQL Statement Execution API tutorial].
 *
 * <p>**Overview of statement execution and result fetching**
 *
 * <p>Statement execution begins by issuing a :method:statementexecution/executeStatement request
 * with a valid SQL statement and warehouse ID, along with optional parameters such as the data
 * catalog and output format.
 *
 * <p>When submitting the statement, the call can behave synchronously or asynchronously, based on
 * the `wait_timeout` setting. When set between 5-50 seconds (default: 10) the call behaves
 * synchronously and waits for results up to the specified timeout; when set to `0s`, the call is
 * asynchronous and responds immediately with a statement ID that can be used to poll for status or
 * fetch the results in a separate call.
 *
 * <p>**Call mode: synchronous**
 *
 * <p>In synchronous mode, when statement execution completes within the `wait timeout`, the result
 * data is returned directly in the response. This response will contain `statement_id`, `status`,
 * `manifest`, and `result` fields. The `status` field confirms success whereas the `manifest` field
 * contains the result data column schema and metadata about the result set. The `result` field
 * contains the first chunk of result data according to the specified `disposition`, and links to
 * fetch any remaining chunks.
 *
 * <p>If the execution does not complete before `wait_timeout`, the setting `on_wait_timeout`
 * determines how the system responds.
 *
 * <p>By default, `on_wait_timeout=CONTINUE`, and after reaching `wait_timeout`, a response is
 * returned and statement execution continues asynchronously. The response will contain only
 * `statement_id` and `status` fields, and the caller must now follow the flow described for
 * asynchronous call mode to poll and fetch the result.
 *
 * <p>Alternatively, `on_wait_timeout` can also be set to `CANCEL`; in this case if the timeout is
 * reached before execution completes, the underlying statement execution is canceled, and a
 * `CANCELED` status is returned in the response.
 *
 * <p>**Call mode: asynchronous**
 *
 * <p>In asynchronous mode, or after a timed-out synchronous request continues, a `statement_id` and
 * `status` will be returned. In this case polling :method:statementexecution/getStatement calls are
 * required to fetch the result and metadata.
 *
 * <p>Next, a caller must poll until execution completes (`SUCCEEDED`, `FAILED`, etc.) by issuing
 * :method:statementexecution/getStatement requests for the given `statement_id`.
 *
 * <p>When execution has succeeded, the response will contain `status`, `manifest`, and `result`
 * fields. These fields and the structure are identical to those in the response to a successful
 * synchronous submission. The `result` field will contain the first chunk of result data, either
 * `INLINE` or as `EXTERNAL_LINKS` depending on `disposition`. Additional chunks of result data can
 * be fetched by checking for the presence of the `next_chunk_internal_link` field, and iteratively
 * `GET` those paths until that field is unset: `GET
 * https://$DATABRICKS_HOST/{next_chunk_internal_link}`.
 *
 * <p>**Fetching result data: format and disposition**
 *
 * <p>To specify the result data format, set the `format` field to `JSON_ARRAY` (JSON),
 * `ARROW_STREAM` ([Apache Arrow Columnar]), or `CSV`.
 *
 * <p>You can also configure how to fetch the result data in two different modes by setting the
 * `disposition` field to `INLINE` or `EXTERNAL_LINKS`.
 *
 * <p>The `INLINE` disposition can only be used with the `JSON_ARRAY` format and allows results up
 * to 16 MiB. When a statement executed with `INLINE` disposition exceeds this limit, the execution
 * is aborted, and no result can be fetched.
 *
 * <p>The `EXTERNAL_LINKS` disposition allows fetching large result sets in `JSON_ARRAY`,
 * `ARROW_STREAM` and `CSV` formats, and with higher throughput.
 *
 * <p>The API uses defaults of `format=JSON_ARRAY` and `disposition=INLINE`. Databricks recommends
 * that you explicit setting the format and the disposition for all production use cases.
 *
 * <p>**Statement response: statement_id, status, manifest, and result**
 *
 * <p>The base call :method:statementexecution/getStatement returns a single response combining
 * `statement_id`, `status`, a result `manifest`, and a `result` data chunk or link, depending on
 * the `disposition`. The `manifest` contains the result schema definition and the result summary
 * metadata. When using `disposition=EXTERNAL_LINKS`, it also contains a full listing of all chunks
 * and their summary metadata.
 *
 * <p>**Use case: small result sets with INLINE + JSON_ARRAY**
 *
 * <p>For flows that generate small and predictable result sets (<= 16 MiB), `INLINE` downloads of
 * `JSON_ARRAY` result data are typically the simplest way to execute and fetch result data.
 *
 * <p>When the result set with `disposition=INLINE` is larger, the result can be transferred in
 * chunks. After receiving the initial chunk with :method:statementexecution/executeStatement or
 * :method:statementexecution/getStatement subsequent calls are required to iteratively fetch each
 * chunk. Each result response contains a link to the next chunk, when there are additional chunks
 * to fetch; it can be found in the field `.next_chunk_internal_link`. This link is an absolute
 * `path` to be joined with your `$DATABRICKS_HOST`, and of the form
 * `/api/2.0/sql/statements/{statement_id}/result/chunks/{chunk_index}`. The next chunk can be
 * fetched by issuing a :method:statementexecution/getStatementResultChunkN request.
 *
 * <p>When using this mode, each chunk may be fetched once, and in order. A chunk without a field
 * `next_chunk_internal_link` indicates the last chunk was reached and all chunks have been fetched
 * from the result set.
 *
 * <p>**Use case: large result sets with EXTERNAL_LINKS + ARROW_STREAM**
 *
 * <p>Using `EXTERNAL_LINKS` to fetch result data in Arrow format allows you to fetch large result
 * sets efficiently. The primary difference from using `INLINE` disposition is that fetched result
 * chunks contain resolved `external_links` URLs, which can be fetched with standard HTTP.
 *
 * <p>**Presigned URLs**
 *
 * <p>External links point to data stored within your workspace's internal DBFS, in the form of a
 * presigned URL. The URLs are valid for only a short period, <= 15 minutes. Alongside each
 * `external_link` is an expiration field indicating the time at which the URL is no longer valid.
 * In `EXTERNAL_LINKS` mode, chunks can be resolved and fetched multiple times and in parallel.
 *
 * <p>----
 *
 * <p>### **Warning: We recommend you protect the URLs in the EXTERNAL_LINKS.**
 *
 * <p>When using the EXTERNAL_LINKS disposition, a short-lived pre-signed URL is generated, which
 * the client can use to download the result chunk directly from cloud storage. As the short-lived
 * credential is embedded in a pre-signed URL, this URL should be protected.
 *
 * <p>Since pre-signed URLs are generated with embedded temporary credentials, you need to remove
 * the authorization header from the fetch requests.
 *
 * <p>----
 *
 * <p>Similar to `INLINE` mode, callers can iterate through the result set, by using the
 * `next_chunk_internal_link` field. Each internal link response will contain an external link to
 * the raw chunk data, and additionally contain the `next_chunk_internal_link` if there are more
 * chunks.
 *
 * <p>Unlike `INLINE` mode, when using `EXTERNAL_LINKS`, chunks may be fetched out of order, and in
 * parallel to achieve higher throughput.
 *
 * <p>**Limits and limitations**
 *
 * <p>Note: All byte limits are calculated based on internal storage metrics and will not match byte
 * counts of actual payloads.
 *
 * <p>- Statements with `disposition=INLINE` are limited to 16 MiB and will abort when this limit is
 * exceeded. - Statements with `disposition=EXTERNAL_LINKS` are limited to 100 GiB. - The maximum
 * query text size is 16 MiB. - Cancelation may silently fail. A successful response from a cancel
 * request indicates that the cancel request was successfully received and sent to the processing
 * engine. However, for example, an outstanding statement may complete execution during signal
 * delivery, with the cancel signal arriving too late to be meaningful. Polling for status until a
 * terminal state is reached is a reliable way to determine the final state. - Wait timeouts are
 * approximate, occur server-side, and cannot account for caller delays, network latency from caller
 * to service, and similarly. - After a statement has been submitted and a statement_id is returned,
 * that statement's status and result will automatically close after either of 2 conditions: - The
 * last result chunk is fetched (or resolved to an external link). - One hour passes with no calls
 * to get the status or fetch the result. Best practice: in asynchronous clients, poll for status
 * regularly (and with backoff) to keep the statement open and alive. - After fetching the last
 * result chunk (including chunk_index=0) the statement is automatically closed.
 *
 * <p>[Apache Arrow Columnar]: https://arrow.apache.org/overview/ [Public Preview]:
 * https://docs.databricks.com/release-notes/release-types.html [SQL Statement Execution API
 * tutorial]: https://docs.databricks.com/sql/api/sql-execution-tutorial.html
 */
@Generated
public class StatementExecutionAPI {
  private static final Logger LOG = LoggerFactory.getLogger(StatementExecutionAPI.class);

  private final StatementExecutionService impl;

  /** Regular-use constructor */
  public StatementExecutionAPI(ApiClient apiClient) {
    impl = new StatementExecutionImpl(apiClient);
  }

  /** Constructor for mocks */
  public StatementExecutionAPI(StatementExecutionService mock) {
    impl = mock;
  }

  public void cancelExecution(String statementId) {
    cancelExecution(new CancelExecutionRequest().setStatementId(statementId));
  }

  /**
   * Cancel statement execution.
   *
   * <p>Requests that an executing statement be canceled. Callers must poll for status to see the
   * terminal state.
   */
  public void cancelExecution(CancelExecutionRequest request) {
    impl.cancelExecution(request);
  }

  /**
   * Execute a SQL statement.
   *
   * <p>Execute a SQL statement, and if flagged as such, await its result for a specified time.
   */
  public ExecuteStatementResponse executeStatement(ExecuteStatementRequest request) {
    return impl.executeStatement(request);
  }

  public GetStatementResponse getStatement(String statementId) {
    return getStatement(new GetStatementRequest().setStatementId(statementId));
  }

  /**
   * Get status, manifest, and result first chunk.
   *
   * <p>This request can be used to poll for the statement's status. When the `status.state` field
   * is `SUCCEEDED` it will also return the result manifest and the first chunk of the result data.
   * When the statement is in the terminal states `CANCELED`, `CLOSED` or `FAILED`, it returns HTTP
   * 200 with the state set. After at least 12 hours in terminal state, the statement is removed
   * from the warehouse and further calls will receive an HTTP 404 response.
   *
   * <p>**NOTE** This call currently may take up to 5 seconds to get the latest status and result.
   */
  public GetStatementResponse getStatement(GetStatementRequest request) {
    return impl.getStatement(request);
  }

  public ResultData getStatementResultChunkN(String statementId, long chunkIndex) {
    return getStatementResultChunkN(
        new GetStatementResultChunkNRequest()
            .setStatementId(statementId)
            .setChunkIndex(chunkIndex));
  }

  /**
   * Get result chunk by index.
   *
   * <p>After the statement execution has `SUCCEEDED`, the result data can be fetched by chunks.
   * Whereas the first chuck with `chunk_index=0` is typically fetched through a `get status`
   * request, subsequent chunks can be fetched using a `get result` request. The response structure
   * is identical to the nested `result` element described in the `get status` request, and
   * similarly includes the `next_chunk_index` and `next_chunk_internal_link` fields for simple
   * iteration through the result set.
   */
  public ResultData getStatementResultChunkN(GetStatementResultChunkNRequest request) {
    return impl.getStatementResultChunkN(request);
  }

  public StatementExecutionService impl() {
    return impl;
  }
}
