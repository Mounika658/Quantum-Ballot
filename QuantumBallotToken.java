package com.quantumballot.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Model representing an anonymous Quantum Ballot Token.
 */
public class QuantumBallotToken {
    private final String tokenId;
    private final String voterHash;
    private final Qubit ballotQubit;
    private final String verificationReceipt;
    private final Instant timestamp;
    private boolean measured;
    private Integer finalChoice;

    public QuantumBallotToken(String voterHash, Qubit ballotQubit) {
        this.tokenId = "QBT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.voterHash = voterHash;
        this.ballotQubit = ballotQubit;
        this.verificationReceipt = "VR-" + UUID.randomUUID().toString().substring(0, 12);
        this.timestamp = Instant.now();
        this.measured = false;
    }

    public String getTokenId() { return tokenId; }
    public String getVoterHash() { return voterHash; }
    public Qubit getBallotQubit() { return ballotQubit; }
    public String getVerificationReceipt() { return verificationReceipt; }
    public Instant getTimestamp() { return timestamp; }
    public boolean isMeasured() { return measured; }
    public Integer getFinalChoice() { return finalChoice; }

    public void setFinalChoice(int choice) {
        this.finalChoice = choice;
        this.measured = true;
    }
}