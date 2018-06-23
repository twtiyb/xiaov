/*
 * Copyright (c) 2012-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.xiaov.service;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.urlfetch.HTTPRequest;
import org.b3log.latke.urlfetch.HTTPResponse;
import org.b3log.latke.urlfetch.URLFetchService;
import org.b3log.latke.urlfetch.URLFetchServiceFactory;
import org.b3log.xiaov.util.XiaoVs;
import org.json.JSONObject;

import java.net.URL;

/**
 * Turing query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, May 29, 2016
 * @since 1.0.0
 */
@Service
public class WkyQueryService {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(WkyQueryService.class.getName());

	/**
	 * URL fetch service.
	 */
	private static final URLFetchService URL_FETCH_SVC = URLFetchServiceFactory.getURLFetchService();
	/**
	 * Turing Robot API.
	 */
	private static final String WKY_API = XiaoVs.getString("wky.api");

	public String chat(String msg) {

		final HTTPRequest request = new HTTPRequest();
		request.setRequestMethod(HTTPRequestMethod.GET);

		try {
			request.setURL(new URL(WKY_API + msg));
			final HTTPResponse response = URL_FETCH_SVC.fetch(request);
			final JSONObject data = new JSONObject(new String(response.getContent(), "UTF-8"));
			final int code = data.optInt("code");
			if (code == 0) {
				return data.optString("result");
			} else {
				throw new Exception("获得数据错误" + response.getContent());
			}
		} catch (final Exception e) {
			LOGGER.log(Level.ERROR, "Chat with Turing Robot failed", e);
		}
		return null;
	}
}
