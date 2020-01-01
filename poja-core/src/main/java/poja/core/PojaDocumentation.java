/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Set;

/**
 * POJA Documenation is generated per class and consists of two parts:
 *
 * <ol>
 * <li>Root API implementation, {@link #api()}</li>
 * <li>Operation implementations, {@link #operations()}</li>
 * </ol>
 *
 * <p>
 * If the processed class is determined to be a potential API implementation but is undocumented, then the documentation
 * will be flagged via {@link #undocumented()}.
 * </p>
 */
public interface PojaDocumentation {
    /**
     * Process the class for specific annotations.
     *
     * @param clazz
     * @param interested class names/annotations that could be potential make the incoming class an API implementation.
     */
    void process(Class<?> clazz, Set<String> interested);

    /**
     * The root API endpoint(s).
     *
     * @return
     */
    ApiDetails api();

    /**
     * The operation(s) that are available under this API.
     *
     * @return
     */
    Set<ApiOperationDetails> operations();

    /**
     * Class(es) that could possibly be API implementations but not documented.
     *
     * @return
     */
    boolean undocumented();
}
