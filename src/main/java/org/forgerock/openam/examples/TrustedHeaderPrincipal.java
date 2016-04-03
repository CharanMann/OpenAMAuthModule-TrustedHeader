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

import java.io.Serializable;
import java.security.Principal;

/**
 * TrustedHeaderPrincipal represents the user entity.
 */
public class TrustedHeaderPrincipal implements Principal, Serializable {
    private final static String COLON = " : ";

    private final String name;

    public TrustedHeaderPrincipal(String name) {

        if (name == null) {
            throw new NullPointerException("illegal null input");
        }

        this.name = name;
    }

    /**
     * Return the LDAP username for this <code>TrustedHeaderPrincipal</code>.
     *
     * @return the LDAP username for this <code>TrustedHeaderPrincipal</code>
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Return a string representation of this <code>TrustedHeaderPrincipal</code>.
     *
     * @return a string representation of this
     * <code>TestAuthModulePrincipal</code>.
     */
    @Override
    public String toString() {
        return new StringBuilder().append(this.getClass().getName())
                .append(COLON).append(name).toString();
    }

    /**
     * Compares the specified Object with this <code>TrustedHeaderPrincipal</code>
     * for equality. Returns true if the given object is also a
     * <code> TrustedHeaderPrincipal </code> and the two TrustedHeaderPrincipal have
     * the same username.
     *
     * @param o Object to be compared for equality with this
     *          <code>TrustedHeaderPrincipal</code>.
     * @return true if the specified Object is equal equal to this
     * <code>TrustedHeaderPrincipal</code>.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof TrustedHeaderPrincipal)) {
            return false;
        }
        TrustedHeaderPrincipal that = (TrustedHeaderPrincipal) o;

        return this.getName().equals(that.getName());
    }

    /**
     * Return a hash code for this <code>TrustedHeaderPrincipal</code>.
     *
     * @return a hash code for this <code>TrustedHeaderPrincipal</code>.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
