/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core.annotation;

/**
 * Annotation for API input parameters.
 */
public @interface ApiParam {
    /**
     * Parameter name.
     *
     * @return
     */
    String name();

    /**
     * The input location can be one of,
     *
     * <ol
     * <li>url - indicates URL parameter</li>
     * <li>body - body input</li>
     * <li>header - header attribute</li>
     * </ol>
     *
     * @return
     */
    String location();

    /**
     * Default to string.
     *
     * @return
     */
    Class<?> type() default String.class;

    /**
     * Additional details on the input model (i.e. to javadoc, confluence, etc).
     *
     * @return
     */
    String url() default "";

    /**
     * Default parameter will be required.
     *
     * @return
     */
    boolean required() default true;
}
