/*
 * Copyright (c) 2016, Eliza Bland
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.shinonomelabs.math.cas;

import java.math.BigDecimal;

/**
 * A class representing a term, in the form ax^n.
 * @author Monaco Bland
 */
public class Term {
    private final BigDecimal coefficient, order;
    private final Variable variable;
    
    public Term(int coef, Variable var, int ord) {
        this(new BigDecimal(String.valueOf(coef)), var, new BigDecimal(String.valueOf(ord)));
    }
    
    public Term(BigDecimal coef, Variable var, BigDecimal ord) {
        this.coefficient = coef;
        this.order = ord;
        this.variable = var;
    }
    
    public Term add(Term t) {
        if(!this.variable.equals(t.variable)) throw new IllegalArgumentException("Terms not in the same variable.");
        if(!this.order.equals(t.order)) throw new IllegalArgumentException("Terms not of the same order."); // TODO polynomial return type
        return new Term(this.coefficient.add(t.coefficient), variable, order);
    }
    
    public BigDecimal getCoefficient() { return coefficient; }
    public BigDecimal getOrder() { return order; }
    public Variable getVariable() { return variable; }
    
    @Override
    public String toString() {
        if(order.equals(BigDecimal.ZERO)) return coefficient.toString();
        else if(order.equals(BigDecimal.ONE)) return coefficient + variable.label;
        else return coefficient + variable.label + "^" + order;
    }
}
