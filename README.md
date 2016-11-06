# CAS
Pure Java computer algebra system

## Features
* Polynomials consisting of one or more terms in the form `ax^n`.
* Differentiation of polynomials.
* Integration of polynomials consisting entirely of terms `ax^n` where `n>0`, but without `+ c` at the end.

## Usage
### Create a variable
Variables are effectively just `String` "wrappers" of sorts. The string is the name of the variable, i.e. the `x` in `ax^n`.
```Java
Variable var = new Variable("x");
```

### Create a term
Terms are in the form `ax^n`, where `a` is the *coefficient*, `x` is the *variable* and `n` is the *order*. The constructor is `new Term(BigDecimal coefficient, Variable variable, BigDecimal order)`. Another constructor, with an `int` coefficient and order, is added for convenience.
```Java
Term t = new Term(3,var,2);
```

### Create a polynomial
A polynomial is a set of terms. They can be differentiated and integrated (albeit a little incorrectly). They are constructed with `new Polynomial(Term... terms)`.
```Java
Term t1 = new Term(3,var,2);
Term t2 = new Term(2,var,1);
Term t3 = new Term(4,var,0);
Polynomial p = new Polynomial(t1,t2,t3);
System.out.println(p);
```
Output:
```
3x^2 + 2x + 4
```

### Differentiate a polynomial
Simply call `differentiate()` on the polynomial you want to differentiate, and cast it to `Polynomial` type.
```Java
Polynomial p = new Polynomial(t1,t2,t3);
Polynomial dpdx = (Polynomial)p.differentiate();
System.out.println(dpdx);
```
Output:
```
6x + 2
```

### Integrate a polynomial
Call `integrate()` on the polynomial you want to integrate, and cast to `Polynomial` type. Attempts to integrate polynomials with one or more terms of `ax^n` where `n<0` will result in an `UnsupportedOperationException` being thrown. Additionally, the resultant polynomial will lack `+ c`, the constant of integration, at the end.
```Java
Polynomial p = new Polynomial(t1,t2,t3);
Polynomial sp = (Polynomial)p.integrate();
System.out.println(sp);
```
Output:
```
x^3 + x^2 + 4x
```
