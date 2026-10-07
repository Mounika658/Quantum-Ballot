package com.quantumballot.model;

import java.security.SecureRandom;

/**
 * Models a single Qubit state |psi> = alpha|0> + beta|1>
 * Implements Quantum Gates (Hadamard, Pauli-X, Pauli-Z) and Basis Measurement.
 */
public class Qubit {
    private Complex alpha; // Amplitude for |0>
    private Complex beta;  // Amplitude for |1>
    private static final SecureRandom random = new SecureRandom();

    public Qubit(Complex alpha, Complex beta) {
        double norm = Math.sqrt(alpha.normSquare() + beta.normSquare());
        this.alpha = alpha.multiply(1.0 / norm);
        this.beta = beta.multiply(1.0 / norm);
    }

    // Factory method for |0>
    public static Qubit zero() {
        return new Qubit(Complex.ONE, Complex.ZERO);
    }

    // Factory method for |1>
    public static Qubit one() {
        return new Qubit(Complex.ZERO, Complex.ONE);
    }

    // Apply Hadamard Gate: H |psi>
    public void applyHadamard() {
        double invSqrt2 = 1.0 / Math.sqrt(2.0);
        Complex newAlpha = alpha.add(beta).multiply(invSqrt2);
        Complex newBeta = alpha.add(beta.multiply(-1.0)).multiply(invSqrt2);
        this.alpha = newAlpha;
        this.beta = newBeta;
    }

    // Apply Pauli-X (NOT) Gate
    public void applyPauliX() {
        Complex temp = this.alpha;
        this.alpha = this.beta;
        this.beta = temp;
    }

    // Measure Qubit in Computational Z Basis (|0> vs |1>)
    public int measureInZBasis() {
        double prob0 = alpha.normSquare();
        double r = random.nextDouble();

        if (r < prob0) {
            this.alpha = Complex.ONE;
            this.beta = Complex.ZERO;
            return 0;
        } else {
            this.alpha = Complex.ZERO;
            this.beta = Complex.ONE;
            return 1;
        }
    }

    public Complex getAlpha() { return alpha; }
    public Complex getBeta() { return beta; }

    @Override
    public String toString() {
        return String.format("(%s)|0⟩ + (%s)|1⟩", alpha, beta);
    }
}