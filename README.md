# ⚛️ Quantum Ballot System (Java & Spring Boot)

An unconditional security quantum voting protocol implemented in **Java 17**. 
This application models quantum key distribution (BB84), superposition state voting tokens, EPR pair entanglement verification, and eavesdropper/tampering detection via Quantum Bit Error Rate (QBER).

---

## 🚀 How to Build and Run

### Prerequisites
* Java JDK 17+
* Apache Maven 3.8+

### Build & Run
```bash
# Clone or download repository
cd quantum-ballot-system

# Build with Maven
mvn clean package

# Execute Spring Boot Application
mvn spring-boot:run
```

The application server will start on `http://localhost:8080`.

---

## 🧪 Running Quantum Protocol Unit Tests

```bash
mvn test
```

---

## 📡 REST API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/api/v1/quantum-ballot/status` | System health & quantum generator status |
| **POST** | `/api/v1/quantum-ballot/register-voter` | Issue anonymous Quantum Key Pair (BB84 basis) |
| **POST** | `/api/v1/quantum-ballot/cast-vote` | Encapsulate vote into qubit superposition state |
| **POST** | `/api/v1/quantum-ballot/tally` | Perform quantum state measurement & verify QBER |
| **GET** | `/api/v1/quantum-ballot/verify-receipt/{receiptId}` | Verify zero-knowledge ballot inclusion |

---

## 🔒 Quantum Security Features
1. **No-Cloning Theorem Compliance**: Qubit ballot states cannot be duplicated by eavesdroppers or rogue talliers.
2. **Superposition Anonymity**: Votes are stored as superposed amplitudes $\alpha|0\rangle + \beta|1\rangle$ until the synchronized measurement phase.
3. **Decoy State & QBER Monitoring**: Detects interception or measurement tampering with $>99.9\%$ probability.
