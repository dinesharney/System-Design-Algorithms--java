# System Design Algorithms - Java

This repository contains Java implementations of fundamental system design algorithms. These algorithms are building blocks for scalable, distributed, and high-performance backend systems.

## 📦 Modules Included

### 1. 🔐 Geohash Encoder & Decoder
- Encode geographic coordinates (latitude, longitude) into a short base32 geohash string.
- Decode a geohash back into the approximate latitude/longitude.
- Get the bounding box (min/max lat & lon) for any geohash.

📄 File: `GeoHash.java`

### 2. 🎯 Consistent Hashing with Virtual Nodes
- Evenly distributes keys across nodes in a distributed system.
- Supports adding/removing nodes with minimal remapping of keys.
- Includes support for virtual nodes to improve load balance.

📄 File: `ConsistentHash.java`

---

## 🛠️ How to Compile & Run

### Requirements
- JDK 8 or above
- No external dependencies

### Compile
```bash
javac GeoHash.java
javac ConsistentHash.java
