/**
 * Provides controller advice for composing rendered views with the shared
 * application layout and exposing route-specific asset names to templates.
 *
 * <p>Spring Boot discovers this package through component scanning. The
 * application's {@code @SpringBootApplication} is declared in the parent
 * {@code com.pma.springbootgraalvm} package, so its scan includes this
 * subpackage. As a result, the {@code @ControllerAdvice} class here is
 * registered automatically as a Spring bean.</p>
 *
 * <p>The advice captures each view's body content, renders it through the
 * common layout template, and supplies page-specific CSS and JavaScript
 * filenames based on the current request path.</p>
 */
package com.pma.springbootgraalvm.advice;
