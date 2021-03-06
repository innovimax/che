/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.languageserver;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.lsp4j.Location;

/**
 * Language service service utilities
 *
 * @author Thomas Mäder
 * @author Vitalii Parfonov
 * @author Yevhen Vydolob
 * @author Dmytro Kulieshov
 */
public class LanguageServiceUtils {

  public static final String PROJECTS = "/projects";
  public static final String FILE_PROJECTS = "file:///projects";

  public static String prefixURI(String uri) {
    return uri.startsWith("/") ? FILE_PROJECTS + uri : uri;
  }

  public static String removePrefixUri(String uri) {
    return uri.startsWith(FILE_PROJECTS) ? uri.substring(FILE_PROJECTS.length()) : uri;
  }

  public static List<String> removePrefixUri(List<String> uris) {
    return uris.stream().map(LanguageServiceUtils::removePrefixUri).collect(Collectors.toList());
  }

  public static String removeUriScheme(String uri) {
    return uri.startsWith(FILE_PROJECTS) ? uri.substring("file://".length()) : uri;
  }

  public static boolean truish(Boolean b) {
    return b != null && b;
  }

  public static boolean isProjectUri(String path) {
    return path.startsWith(FILE_PROJECTS);
  }

  public static boolean isStartWithProject(String path) {
    return path.startsWith(PROJECTS);
  }

  public static String prefixProject(String path) {
    return path.startsWith(PROJECTS) ? path : PROJECTS + path;
  }

  public static String extractProjectPath(String fileUri) throws LanguageServerException {
    if (!isProjectUri(fileUri)) {
      throw new LanguageServerException("Not a project URI: " + fileUri);
    }
    Path path = Paths.get(removeUriScheme(fileUri));
    if (path.getNameCount() < 1) {
      throw new LanguageServerException("Not a project URI: " + fileUri);
    }
    return path.subpath(0, 2).toString();
  }

  public static Location fixLocation(Location o) {
    o.setUri(removePrefixUri(o.getUri()));
    return o;
  }

  public static boolean isWorkspaceUri(String uri) {
    return uri.startsWith("/");
  }

  public static String workspaceURIToFileURI(String uri) throws LanguageServerException {
    try {
      return FILE_PROJECTS + new URI(uri).getPath();
    } catch (URISyntaxException e) {
      throw new LanguageServerException("malformed uri", e);
    }
  }

  public static String fixUri(String uri) {
    return isProjectUri(uri) ? removePrefixUri(uri) : uri;
  }

  public static String fixJdtUri(String uri) {
    if (uri.startsWith("jdt:/") && !uri.startsWith("jdt://")) {
      uri = uri.replace("jdt:/", "jdt://");
    }

    return uri;
  }
}
