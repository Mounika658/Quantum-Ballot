package com.quantumballot;

import com.quantumballot.model.Complex;
import com.quantumballot.model.Qubit;
import com.quantumballot.service.QuantumKeyDistributionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantumBallotTest {

    @Test
    @DisplayName("Hadamard transform converts |0> into (|0> + |1>)/sqrt(2)")
    void testHadamardState() {
        Qubit q = Qubit.zero();
        q.applyHadamard();

        double expectedAmp = 1.0 / Math.sqrt(2.0);
        assertEquals(expectedAmp, q.getAlpha().getReal(), 1e-5);
        assertEquals(expectedAmp, q.getBeta().getReal(), 1e-5);
    }

    @Test
    @DisplayName("BB84 protocol detects eavesdropper when Eve intercepts qubits")
    void testEavesdropperDetection() {
        QuantumKeyDistributionService qkd = new QuantumKeyDistributionService();
        var normalResult = qkd.executeBB84Protocol(64, false);
        var eveResult = qkd.executeBB84Protocol(64, true);

        assertTrue(normalResult.errorRate < 0.10, "Normal channel should have low QBER");
        assertTrue(eveResult.errorRate > 0.15 || eveResult.eavesdropperDetected,
                "Eavesdropping should elevate QBER significantly");
    }
}