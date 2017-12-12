/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */

package org.eclipse.che.ide.js.api.workspace.event;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import org.eclipse.che.ide.api.eventbus.EventType;
import org.eclipse.che.ide.api.workspace.event.ServerRunningEvent;

/** @author Yevhen Vydolob */
@JsType(name = "ServerRunningEvent")
public class JsServerRunningEvent {

  public static final EventType<JsServerRunningEvent> TYPE = () -> "server.running";

  @JsIgnore private final ServerRunningEvent event;

  @JsIgnore
  public JsServerRunningEvent(ServerRunningEvent event) {
    this.event = event;
  }

  /** Returns the running server's name. */
  @JsMethod
  public String getServerName() {
    return event.getServerName();
  }

  /** Returns the related machine's name. */
  @JsMethod
  public String getMachineName() {
    return event.getMachineName();
  }
}
