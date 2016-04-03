/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2016 Charan Mann
 * Portions Copyrighted 2016 ForgeRock AS
 *
 * OpenAMAuthModule-TrustedHeader: Created by Charan Mann on 4/1/16 , 9:37 AM.
 */

package org.forgerock.openam.examples;

import com.sun.identity.authentication.spi.AMLoginModule;
import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.util.ISAuthConstants;
import com.sun.identity.shared.datastruct.CollectionHelper;
import com.sun.identity.shared.debug.Debug;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Map;


/**
 * TrustedHeader OpenAM custom authentication module example. This module checks the presence of configured trusted header in the request.
 *
 * Note that this is just a sample implementation for demo purposes.
 */
public class TrustedHeader extends AMLoginModule {

    // Name for the debug-log
    private final static String DEBUG_NAME = "TrustedHeader";
    private final static Debug debug = Debug.getInstance(DEBUG_NAME);
    private static final String TRUSTED_HEADER_NAME_ATTR = "X-Special-Trusted-User";
    private static final String AUTH_LEVEL_ATTR = "iplanet-am-auth-trustedheader-auth-level";

    private String trustedHeaderName;
    private String userId;
    private String authLevel = null;

    /**
     * Default constructor
     */
    public TrustedHeader() {
        super();
    }


    /**
     * This method stores service attributes and localized properties for later
     * use.
     *
     * @param subject
     * @param sharedState
     * @param options
     */
    @Override
    public void init(Subject subject, Map sharedState, Map options) {

        debug.message("TrustedHeader::init");

        this.trustedHeaderName = CollectionHelper.getMapAttr(options,
                TRUSTED_HEADER_NAME_ATTR, "X-Special-Trusted-User");
        debug.message("TRUSTED_HEADER_NAME_ATTR attribute: " + trustedHeaderName);

        authLevel = CollectionHelper.getMapAttr(options, AUTH_LEVEL_ATTR);
        debug.message("AUTH_LEVEL_ATTR attribute: " + authLevel);

        if (authLevel != null) {
            try {
                setAuthLevel(Integer.parseInt(authLevel));
            } catch (Exception e) {
                debug.error("{}: Unable to set auth level {}", "TrustedAuth", authLevel);
            }
        }
    }

    @Override
    public int process(Callback[] callbacks, int state) throws LoginException {

        debug.message("TrustedHeader::process state: {}", state);

        HttpServletRequest request = getHttpServletRequest();


        Enumeration headers = request.getHeaderNames();
        debug.message("Looking for header: " + trustedHeaderName);
        while (headers.hasMoreElements()) {
            String headerName = (String) headers.nextElement();
            if (headerName.equalsIgnoreCase(trustedHeaderName)) {

                userId = request.getHeader(headerName);
                debug.message("Trusted header found for user: " + userId);
            }
        }
        if (userId == null) {
            throw new AuthLoginException("No trusted header found");
        }
        return ISAuthConstants.LOGIN_SUCCEED;

    }

    @Override
    public Principal getPrincipal() {
        return new TrustedHeaderPrincipal(userId);
    }

}
