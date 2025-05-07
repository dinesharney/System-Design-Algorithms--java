# System Design Algorithms - Java

This repository contains Java implementations of fundamental system design algorithms. These algorithms are foundational for building scalable, distributed, and high-performance backend systems.

---

## 📦 Modules Included

### 1. 🔐 Geohash Encoder & Decoder
- Encode geographic coordinates into a compact base32 geohash string.
- Decode a geohash back to approximate coordinates.
- Compute the bounding box for spatial queries.

📄 File: `GeoHash.java`

---

### 2. 🎯 Consistent Hashing with Virtual Nodes
- Distributes keys evenly across nodes with minimal reshuffling.
- Adds support for virtual nodes for smoother key distribution.
- Commonly used in distributed caches and sharded databases.

📄 File: `ConsistentHash.java`

---

### 3. 🚰 Leaky Bucket Rate Limiter
- Smooths out bursts of traffic by leaking requests at a fixed rate.
- Rejects requests when the bucket is full.
- Useful for network traffic shaping and burst control.

📄 File: `LeakyBucket.java`

---

### 4. 🪣 Token Bucket Rate Limiter
- Allows a burst of traffic up to a token capacity.
- Replenishes tokens at a steady rate.
- Ideal for API rate limiting with sustained throughput and burst tolerance.

📄 File: `TokenBucket.java`

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
javac TokenBucket.java
