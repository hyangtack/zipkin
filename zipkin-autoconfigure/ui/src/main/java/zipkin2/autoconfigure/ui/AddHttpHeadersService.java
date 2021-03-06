/*
 * Copyright 2015-2019 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin2.autoconfigure.ui;

import com.linecorp.armeria.common.HttpHeaders;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Service;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingService;

/** Decorates a {@link Service} with additional HTTP headers upon success. */
final class AddHttpHeadersService extends SimpleDecoratingService<HttpRequest, HttpResponse> {

  final HttpHeaders toAdd;

  AddHttpHeadersService(Service<HttpRequest, HttpResponse> delegate, HttpHeaders toAdd) {
    super(delegate);
    this.toAdd = toAdd;
  }

  @Override public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
    ctx.addAdditionalResponseHeaders(toAdd);
    return delegate().serve(ctx, req);
  }
}
