/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.aether;

import java.util.ArrayList;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.model.Exclusion;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link RootArtifact}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class RootArtifactTest {

    /**
     * Temp dir.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @Rule
    public final transient TemporaryFolder temp = new TemporaryFolder();

    /**
     * RootArtifact can resolve a root artifact.
     * @throws Exception If there is some problem inside
     */
    @Test
    @Ignore
    @SuppressWarnings("unchecked")
    public void resolvesRootArtifact() throws Exception {
        final RootArtifact root = new RootArtifact(
            // @checkstyle MultipleStringLiterals (1 line)
            new DefaultArtifact(
                // @checkstyle MultipleStringLiteralsCheck (1 line)
                "junit", "junit", "4.10", "", "jar", "",
                new DefaultArtifactHandler()
            ),
            new ArrayList<Exclusion>()
        );
        MatcherAssert.assertThat(
            root,
            Matchers.hasToString(Matchers.containsString("junit:junit:4.10"))
        );
        MatcherAssert.assertThat(
            root.children(),
            Matchers.<Artifact>hasItems(
                Matchers.hasToString("junit:junit:jar:4.10")
            )
        );
    }

    /**
     * RootArtifact can gracefully resolve a root artifact.
     * @throws Exception If there is some problem inside
     */
    @Test
    @Ignore
    public void gracefullyResolvesBrokenRootArtifact() throws Exception {
        final RootArtifact root = new RootArtifact(
            new DefaultArtifact(
                "junit-broken", "junit-absent", "1.0", "", "", "",
                new DefaultArtifactHandler()
            ),
            new ArrayList<Exclusion>()
        );
        MatcherAssert.assertThat(
            root,
            Matchers.hasToString(
                Matchers.containsString("failed to load 'junit-broken:")
            )
        );
    }
}
