/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Set;

public interface PojaDocumentBuilder {
    /**
     * Process the class for specific annotations.
     *
     * @param clazz
     */
    void process(Class<?> clazz);

    /**
     * The root API endpoint(s).
     *
     * @return
     */
    ApiBuilder api();

    /**
     * The operation(s) that are available under this API.
     *
     * @return
     */
    Set<ApiOperationBuilder> operations();
}
