package com.markitserv.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JacksonAnnotationsInside
@JsonIgnore
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestLink {
	String relUri();
}
