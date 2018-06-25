/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.quickstart.appjee;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
public class ServiceLocator {

       
       private static final String SERVICE_URI_INIT_PARAM_NAME = "serviceUrl";

       public static URL getServiceUrl(HttpServletRequest req) {
               URL serviceUrl = null;
               
               try {
                       String url = req.getServletContext().getInitParameter(SERVICE_URI_INIT_PARAM_NAME);
                   if (url != null && !url.contains("localhost")){
                       try {
                                       serviceUrl = new URL(url);
                                       return serviceUrl;
                               } catch (MalformedURLException e){
                                       throw new RuntimeException("Malformed URL: " + url);
                               }
                   }
       
                       String host = req.getLocalAddr();
       
                       if (host.equals("localhost")) {
                               try {
                                       host = java.net.InetAddress.getLocalHost().getHostAddress();
                               } catch (Exception e) {
                               }
                       }
                       
                       try {
                               serviceUrl = new URL("http://" + host + ":8080/service");
                               return serviceUrl;
                       } catch (MalformedURLException e){
                               throw new RuntimeException("Malformed URL: " + host);
                       }
               } finally {
                       System.out.println("Using Service URL " + serviceUrl);
               }
       }

}
