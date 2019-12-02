/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.component.tagsoup.it;

import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class TagSoupRouteBuilder extends RouteBuilder {
    public static final String DIRECT_HTML_TO_DOM = "direct:html-to-dom";

    @Override
    public void configure() {
        from(DIRECT_HTML_TO_DOM)
                .unmarshal().tidyMarkup()
                .process(exchange -> {
                    Document doc = exchange.getIn().getBody(Document.class);
                    XPath xPath = XPathFactory.newInstance().newXPath();
                    String result = xPath.compile("//p/text()").evaluate(doc);
                    exchange.getIn().setBody(result);
                });
    }
}
