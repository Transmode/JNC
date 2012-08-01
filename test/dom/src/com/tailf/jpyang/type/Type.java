/*    -*- Java -*-
 *
 *  Copyright 2012 Tail-F Systems AB. All rights reserved.
 *
 *  This software is the confidential and proprietary
 *  information of Tail-F Systems AB.
 *
 *  $Id$
 *
 */

package com.tailf.jpyang.type;

import java.math.BigDecimal;

import com.tailf.jpyang.ConfMException;

/**
 * Represents a built-in YANG data type.
 * 
 * @author emil@tail-f.com
 */
abstract class Type<T> implements java.io.Serializable {

    /**
     * Generated serial version UID, to be changed if this class is modified in
     * a way which affects serialization. Please see:
     * http://docs.oracle.com/javase/6/docs/platform/serialization/spec/version.html#6678
     */
    private static final long serialVersionUID = 1283676367920670186L;

    /**
     * The value of this object, of which this class is a wrapper for.
     * 
     * @serial
     */
    protected T value;
    
    /**
     * Empty constructor for a YangType object. The value will not be
     * initialized when calling this method.
     */
    public Type() {
    }

    /**
     * Creates a YangType object from a String.
     * 
     * @param s The string.
     * @throws ConfMException If an invariant was broken during initialization,
     *                        or if value could not be parsed from s.
     */
    public Type(String s) throws ConfMException {
        setValue(s);
    }

    /**
     * Creates a YangType object from a value of type T.
     * 
     * @param value The initial value of the new YangType object.
     * @throws ConfMException If an invariant was broken during initialization.
     */
    public Type(T value) throws ConfMException {
        setValue(value);
    }

    /**
     * Sets the value of this object using a String.
     * 
     * @param s A string containing the new value to set.
     * @throws ConfMException If an invariant was broken during assignment, or
     *                        if value could not be parsed from s.
     */
    public void setValue(String s) throws ConfMException {
        s = TypeUtil.wsCollapse(s);
        setValue(fromString(s));
    }

    /**
     * Sets the value of this object using a value of type T.
     * 
     * @param value The new value to set.
     * @throws ConfMException If an invariant was broken during assignment.
     */
    public void setValue(T value) throws ConfMException {
        assert !(value instanceof Type): "Avoid circular value chain";
        this.value = value;
        check();
    }

    /**
     * @return The value of this object.
     */
    public T getValue() {
        return value;
    }

    /**
     * Checks that the value of this object is not null. Called in constructors
     * and value setters. Subclasses that have state invariants should extend
     * this method and throw a ConfMException if such an invariant has been
     * violated.
     * 
     * @throws ConfMException If the value of this object is null.
     */
    public void check() throws ConfMException {
        ConfMException.throwException(getValue() == null, this);
    }

    /**
     * @return The value of this object, as a String.
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Returns a value of type T given a String.
     * <p>
     * Note: No implementation should use this.value - this method would be
     * static if Java allowed for abstract static classes.
     * 
     * @param s A string representation of a value of type T.
     * @return A T value parsed from s.
     * @throws ConfMException If s does not contain a parsable T.
     */
    protected abstract T fromString(String s) throws ConfMException;

    /**
     * Compares this object with another object for equality.
     * 
     * @param obj The object to compare with.
     * @return true if obj can be cast to a comparable type and the value of
     *         this object is equal to the value of obj; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (value == null || !canEqual(obj)) {
            return false;
        }
        if (obj instanceof Type<?>) {
            Type<?> other = (Type<?>) obj;
            if (!other.canEqual(this))
                return false;
            obj = other.getValue();
        }
        if (value instanceof Number && obj instanceof Number) {
            BigDecimal valueNumber = TypeUtil.bigDecimalValueOf((Number) value);
            BigDecimal objNumber = TypeUtil.bigDecimalValueOf((Number) obj);
            return valueNumber.compareTo(objNumber) == 0;
        }
        return value.equals(obj);
    }
    
    /**
     * Compares type of obj with this object to see if they can be equal.
     * 
     * @param obj Object to compare type with.
     * @return true if obj type is compatible; false otherwise.
     */
    public abstract boolean canEqual(Object obj);

    @Override
    public int hashCode() {
        if (value == null) 
            return 0;
        return value.hashCode();
    }

    /** ---------- Restrictions ---------- */

    /**
     * Checks that the value of this object equals or has the same length as
     * the provided other value.
     * 
     * @param other The integer value to compare against.
     * @throws ConfMException If the comparison does not evaluate to true.
     */
    protected void exact(int other) throws ConfMException {
        TypeUtil.restrict(this.value, other, TypeUtil.Operator.EQ);
    }

    /**
     * Checks that the value of this object is not smaller than the min-value.
     * 
     * @param min The min-value to compare against.
     * @throws ConfMException if value is smaller than min.
     */
    protected void min(int min) throws ConfMException {
        TypeUtil.restrict(value, min, TypeUtil.Operator.GR);
    }

    /**
     * Checks that the value of this object is not larger than the max-value.
     * 
     * @param max The max-value to compare against.
     * @throws ConfMException if value is larger than max.
     */
    protected void max(int max) throws ConfMException {
        TypeUtil.restrict(value, max, TypeUtil.Operator.LT);
    }

}