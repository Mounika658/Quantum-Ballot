package com.quantumballot.controller;

import com.quantumballot.model.QuantumBallotToken;
import com.quantumballot.service.QuantumKeyDistributionService;
import com.quantumballot.service.QuantumTallierService;
import com.quantumballot.service.QuantumVotingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quantum-ballot")
@CrossOrigin(origins = "*")
public class QuantumBallotController {

    private final QuantumKeyDistributionService qkdService;
    private final QuantumVotingService votingService;
    private final QuantumTallierService tallierService;

    public QuantumBallotController(
            QuantumKeyDistributionService qkdService,
            QuantumVotingService votingService,
            QuantumTallierService tallierService) {
        this.qkdService = qkdService;
        this.votingService = votingService;
        this.tallierService = tallierService;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("protocol", "BB84 Anonymous Quantum Voting");
        status.put("version", "1.0.0-JAVA");
        status.put("status", "ACTIVE_READY");
        return ResponseEntity.ok(status);
    }

    @PostMapping("/simulate-qkd")
    public ResponseEntity<QuantumKeyDistributionService.QKDResult> simulateQKD(
            @RequestParam(defaultValue = "16") int numBits,
            @RequestParam(defaultValue = "false") boolean simulateEve) {
        var result = qkdService.executeBB84Protocol(numBits, simulateEve);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/cast-vote")
    public ResponseEntity<QuantumBallotToken> castVote(
            @RequestParam String voterHash,
            @RequestParam int choice) {
        QuantumBallotToken token = votingService.castQuantumVote(voterHash, choice);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/tally")
    public ResponseEntity<QuantumTallierService.TallyResult> tallyElection() {
        String[] candidates = {"Alice (Quantum Party)", "Bob (Cyber Security Party)"};
        var result = tallierService.measureAndTally(votingService.getAllBallots(), candidates);
        return ResponseEntity.ok(result);
    }
}