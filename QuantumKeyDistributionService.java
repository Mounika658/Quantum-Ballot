package com.quantumballot.service;

import com.quantumballot.model.Complex;
import com.quantumballot.model.Qubit;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements BB84 Quantum Key Distribution (QKD)
 * Establishes tamper-proof anonymous quantum channels between voters and election talliers.
 */
@Service
public class QuantumKeyDistributionService {
    private final SecureRandom random = new SecureRandom();

    public static class QKDResult {
        public List<Integer> sharedKey;
        public double errorRate;
        public boolean eavesdropperDetected;

        public QKDResult(List<Integer> sharedKey, double errorRate, boolean eavesdropperDetected) {
            this.sharedKey = sharedKey;
            this.errorRate = errorRate;
            this.eavesdropperDetected = eavesdropperDetected;
        }
    }

    public QKDResult executeBB84Protocol(int numBits, boolean simulateEve) {
        List<Integer> aliceBits = new ArrayList<>();
        List<String> aliceBases = new ArrayList<>();
        List<Qubit> quantumChannel = new ArrayList<>();

        // Step 1: Alice generates random bits and bases (+ or x)
        for (int i = 0; i < numBits; i++) {
            int bit = random.nextInt(2);
            boolean isDiagonal = random.nextBoolean();
            aliceBits.add(bit);
            aliceBases.add(isDiagonal ? "X" : "Z");

            Qubit q = (bit == 0) ? Qubit.zero() : Qubit.one();
            if (isDiagonal) {
                q.applyHadamard(); // Transform to |+> or |->
            }
            quantumChannel.add(q);
        }

        // Step 2: Eve (Intercepter) attempts eavesdropping
        if (simulateEve) {
            for (Qubit q : quantumChannel) {
                if (random.nextDouble() < 0.7) { // Eve measures 70% of qubits
                    if (random.nextBoolean()) q.applyHadamard();
                    q.measureInZBasis();
                }
            }
        }

        // Step 3: Bob chooses random bases and measures
        List<String> bobBases = new ArrayList<>();
        List<Integer> bobResults = new ArrayList<>();

        for (int i = 0; i < numBits; i++) {
            boolean bobDiagonal = random.nextBoolean();
            bobBases.add(bobDiagonal ? "X" : "Z");
            Qubit q = quantumChannel.get(i);
            if (bobDiagonal) q.applyHadamard();
            bobResults.add(q.measureInZBasis());
        }

        // Step 4: Sifting key based on matching bases
        List<Integer> finalKey = new ArrayList<>();
        int mismatches = 0;
        int checkedBits = 0;

        for (int i = 0; i < numBits; i++) {
            if (aliceBases.get(i).equals(bobBases.get(i))) {
                if (aliceBits.get(i).equals(bobResults.get(i))) {
                    finalKey.add(aliceBits.get(i));
                } else {
                    mismatches++;
                }
                checkedBits++;
            }
        }

        double qber = checkedBits == 0 ? 0 : (double) mismatches / checkedBits;
        boolean eveDetected = qber > 0.11; // Standard QBER threshold for Eve intrusion

        return new QKDResult(finalKey, qber, eveDetected);
    }
}