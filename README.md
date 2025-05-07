# System Design Algorithms - Java

This repository contains Java implementations of fundamental system design algorithms. These algorithms are foundational for building scalable, distributed, and high-performance backend systems.

---

## 📦 Modules Included

### 1. 🔐 Geohash Encoder & Decoder
- Encode geographic coordinates (latitude, longitude) into a compact base32 geohash string.
- Decode a geohash back to an approximate lat/lon.
- Compute the bounding box for spatial indexing.

📄 File: `GeoHash.java`

---

### 2. 🎯 Consistent Hashing with Virtual Nodes
- Distributes keys across nodes evenly with minimal rebalancing.
- Handles node addition/removal with minimal disruption.
- Supports virtual nodes for smoother distribution.

📄 File: `ConsistentHash.java`

---

### 3. 🚰 Leaky Bucket Rate Limiter
- Controls request rate over time using a fixed-size bucket.
- Leaks requests at a constant rate (e.g., 2/sec).
- Rejects requests if the bucket overflows.

📄 File: `LeakyBucket.java`

---

## 🛠️ How to Compile & Run

### Requirements
- JDK 8 or above
- No external dependencies

### Compile
```bash
javac GeoHash.java
javac ConsistentHash.java
javac LeakyBucket.java
