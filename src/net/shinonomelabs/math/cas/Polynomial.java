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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A collection of terms that can be differentiated and (kinda incorrectly) integrated.
 * @author Monaco Bland
 */
public class Polynomial implements Differentiable, Integratable {
    private final TreeMap<BigDecimal,Term> terms = new TreeMap<>();
    
    public Polynomial(Term... terms) {
        for(Term t : terms) {
            if(this.terms.containsKey(t.getOrder())) {
                Term e = this.terms.get(t.getOrder());
                this.terms.put(t.getOrder(), t.add(e));
            } else this.terms.put(t.getOrder(), t);
        }
    }
    
    
    
    @Override
    public String toString() {
        String out = "";
        Iterator<BigDecimal> it = terms.descendingKeySet().iterator();
        int tc=0;
        boolean isFirstNeg=false;
        while(it.hasNext()) {
            tc++;
            BigDecimal key = it.next();
            Term t = terms.get(key);
            if(t.getCoefficient().equals(BigDecimal.ZERO)) continue;
            if(tc==1) isFirstNeg=t.getCoefficient().compareTo(BigDecimal.ZERO)==-1;
            if(t.getCoefficient().compareTo(BigDecimal.ZERO)==1) out += " +";
            out += t.toString();
        }
        if(!isFirstNeg) out = out.substring(2);
        return out;
    }
    
    /**
     * Differentiates this polynomial.
     * @return first derivative of this <code>Polynomial</code>
     */
    @Override
    public Object differentiate() {
        Iterator<Map.Entry<BigDecimal,Term>> it = terms.entrySet().iterator();
        Term[] newTerms = new Term[0];
        while(it.hasNext()) {
            Map.Entry<BigDecimal,Term> pair = it.next();
            Term t = pair.getValue();
            if(t.getOrder().equals(BigDecimal.ZERO)) continue;
            Term[] newNewTerms = new Term[newTerms.length+1];
            System.arraycopy(newTerms,0,newNewTerms,0,newTerms.length);
            newNewTerms[newNewTerms.length-1] = new Term(t.getCoefficient().multiply(t.getOrder()),t.getVariable(),t.getOrder().subtract(BigDecimal.ONE));
            newTerms = newNewTerms;
        }
        return new Polynomial(newTerms);
    }
    
    /**
     * Integrates this polynomial. Doesn't currently work if any term ax^n has n&lt;0, and doesn't include the constant of integration at the end.
     * @return indefinite integral of this <code>Polynomial</code> if all <code>Term</code>s have order&gt;0 without constant of integration. Throws <code>UnsupportedOperationException</code> otherwise.
     */
    @Override
    public Object integrate() {
        Iterator<Map.Entry<BigDecimal,Term>> it = terms.entrySet().iterator();
        Term[] newTerms = new Term[0];
        while(it.hasNext()) {
            Map.Entry<BigDecimal,Term> pair = it.next();
            Term t = pair.getValue();
            if(t.getOrder().compareTo(BigDecimal.ZERO)==-1) throw new UnsupportedOperationException("Integrations of x^n for n<0 not yet supported.");
            Term[] newNewTerms = new Term[newTerms.length+1];
            System.arraycopy(newTerms,0,newNewTerms,0,newTerms.length);
            newNewTerms[newNewTerms.length-1] = new Term(t.getCoefficient().divide(t.getOrder().add(BigDecimal.ONE)),t.getVariable(),t.getOrder().add(BigDecimal.ONE));
            newTerms = newNewTerms;
        }
        return new Polynomial(newTerms);
    }
}
