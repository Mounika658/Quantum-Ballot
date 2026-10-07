package com.quantumballot.model;

import java.util.Objects;

/**
 * Mathematical representation of a Complex Number (a + bi)
 * Used to model Quantum Amplitudes alpha and beta.
 */
public class Complex {
    private final double real;
    private final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public static Complex ZERO = new Complex(0, 0);
    public static Complex ONE = new Complex(1, 0);

    public double getReal() { return real; }
    public double getImag() { return imag; }

    public double normSquare() {
        return real * real + imag * imag;
    }

    public Complex add(Complex b) {
        return new Complex(this.real + b.real, this.imag + b.imag);
    }

    public Complex multiply(double factor) {
        return new Complex(this.real * factor, this.imag * factor);
    }

    public Complex multiply(Complex b) {
        return new Complex(
            this.real * b.real - this.imag * b.imag,
            this.real * b.imag + this.imag * b.real
        );
    }

    @Override
    public String toString() {
        if (Math.abs(imag) < 1e-9) return String.format("%.3f", real);
        return String.format("%.3f + %.3fi", real, imag);
    }
}