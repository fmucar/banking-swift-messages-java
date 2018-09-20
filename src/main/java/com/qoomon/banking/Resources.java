package com.qoomon.banking;

import java.net.URL;

public final class Resources {

    private Resources() {
    }

    public static URL getResource(String resourceName) {
        ClassLoader loader = MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader());
        URL url = loader.getResource(resourceName);
        Preconditions.checkArgument(url != null, "resource %s not found.", resourceName);
        return url;
    }
}
