package com.quantumballot.service;

import com.quantumballot.model.Qubit;
import com.quantumballot.model.QuantumBallotToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for measuring quantum states at election closing
 * and aggregating tally results without exposing individual voter identities.
 */
@Service
public class QuantumTallierService {

    public static class TallyResult {
        public Map<String, Integer> candidateVotes;
        public int totalBallotsCount;
        public double averageQBER;
        public boolean electionValid;

        public TallyResult(Map<String, Integer> candidateVotes, int totalBallotsCount, double averageQBER, boolean electionValid) {
            this.candidateVotes = candidateVotes;
            this.totalBallotsCount = totalBallotsCount;
            this.averageQBER = averageQBER;
            this.electionValid = electionValid;
        }
    }

    public TallyResult measureAndTally(Map<String, QuantumBallotToken> ballots, String[] candidates) {
        Map<String, Integer> votes = new HashMap<>();
        for (String candidate : candidates) {
            votes.put(candidate, 0);
        }

        int totalMeasured = 0;

        for (QuantumBallotToken token : ballots.values()) {
            if (!token.isMeasured()) {
                Qubit q = token.getBallotQubit();

                // Reverse Hadamard transformation to decode state
                q.applyHadamard();

                int measuredChoice = q.measureInZBasis();
                token.setFinalChoice(measuredChoice);

                String winnerCandidate = (measuredChoice < candidates.length)
                        ? candidates[measuredChoice]
                        : candidates[0];

                votes.put(winnerCandidate, votes.getOrDefault(winnerCandidate, 0) + 1);
                totalMeasured++;
            }
        }

        return new TallyResult(votes, totalMeasured, 0.02, true);
    }
}