package com.quantumballot.service;

import com.quantumballot.model.Complex;
import com.quantumballot.model.Qubit;
import com.quantumballot.model.QuantumBallotToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Quantum Ballot Casting Service.
 * Prepares quantum states representing choices and issues zero-knowledge receipts.
 */
@Service
public class QuantumVotingService {
    private final Map<String, QuantumBallotToken> ballotRegistry = new ConcurrentHashMap<>();

    /**
     * Prepares a Quantum Ballot Token.
     * Choice 0 -> Candidate A (|0>), Choice 1 -> Candidate B (|1>)
     * Applies Hadamard superposition for secret transmission.
     */
    public QuantumBallotToken castQuantumVote(String voterHash, int candidateChoice) {
        Qubit qubit = (candidateChoice == 0) ? Qubit.zero() : Qubit.one();

        // Superpose qubit for blind transmission
        qubit.applyHadamard();

        QuantumBallotToken token = new QuantumBallotToken(voterHash, qubit);
        ballotRegistry.put(token.getTokenId(), token);
        return token;
    }

    public QuantumBallotToken getBallotToken(String tokenId) {
        return ballotRegistry.get(tokenId);
    }

    public Map<String, QuantumBallotToken> getAllBallots() {
        return ballotRegistry;
    }
}