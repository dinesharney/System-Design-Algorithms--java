# System Design Algorithms - Java

This repository contains Java implementations of fundamental system design algorithms. These are essential building blocks for scalable, distributed, and high-performance backend systems.

---

## 📆 Modules Included

### 1. 🔐 Geohash Encoder & Decoder

* Encode latitude & longitude into a compact base32 geohash.
* Decode geohash to approximate coordinates.
* Calculate the bounding box for efficient spatial querying.

📄 File: `GeoHash.java`

---

### 2. 🎯 Consistent Hashing with Virtual Nodes

* Distributes keys evenly across nodes.
* Minimizes rebalancing when nodes are added/removed.
* Supports virtual nodes for smoother key distribution.

📄 File: `ConsistentHash.java`

---

### 3. 🚰 Leaky Bucket Rate Limiter

* Smooths out burst traffic by leaking requests at a fixed rate.
* Rejects requests when the bucket is full.
* Useful for network traffic shaping and request throttling.

📄 File: `LeakyBucket.java`

---

### 4. 🪣 Token Bucket Rate Limiter

* Allows bursts of traffic up to a configured limit.
* Refills tokens over time at a steady rate.
* Enforces sustained throughput while tolerating short spikes.

📄 File: `TokenBucket.java`

---

### 5. 📖 Trie (Prefix Tree)

* Inserts and searches words efficiently using character-by-character traversal.
* Supports prefix-based search and auto-complete features.
* Ideal for dictionary storage, spell checkers, and predictive text systems.

📄 File: `Trie.java`

---

### 6. 📁 Rsync (Incremental File Sync)

* Computes rolling and strong checksums (MD5) to detect changes.
* Syncs only the modified blocks instead of the entire file.
* Demonstrates efficient file comparison and delta transfer.

📄 File: `Rsync.java`

---

## 🛠️ How to Compile & Run

### Requirements

* JDK 8 or higher
* No external dependencies

### Compile

```bash
javac GeoHash.java
javac ConsistentHash.java
javac LeakyBucket.java
javac TokenBucket.java
javac Trie.java
javac Rsync.java
```

### Run

```bash
java GeoHash
java ConsistentHash
java LeakyBucket
java TokenBucket
java Trie
java Rsync
```

---

## 🧶 Sample Output

### 🗚️ Geohash

```
Encoded Geohash: 9q8yyz0rc
Decoded Center: Lat = 37.774899, Lon = -122.419396
Bounding Box: Lat: [37.774887, 37.774911], Lon: [-122.419434, -122.419357]
```

### ⚙️ Consistent Hashing

```
Key1 => NodeB
Key2 => NodeC
Key3 => NodeA

Adding NodeD...

Key1 => NodeD
Key2 => NodeC
Key3 => NodeA
```

### 🚦 Leaky Bucket

```
Request 01: ACCEPTED
Request 02: ACCEPTED
...
Request 11: REJECTED
```

### 🪙 Token Bucket

```
Request 01: ACCEPTED
Request 02: ACCEPTED
...
Request 06: REJECTED
```

### 🔮 Trie

```
Search 'hello': true
Search 'helix': false
StartsWith 'he': true
StartsWith 'ho': false
```

### 🔹 Rsync

```
Comparing files...
Modified Blocks:
→ Block 2 differs
→ Block 5 differs
```

---

## 📚 Concepts Explained

* **Geohash** – Efficient geospatial indexing and lookup
* **Consistent Hashing** – Load balancing with minimal disruption
* **Leaky Bucket** – Smooths out traffic with fixed leak rate
* **Token Bucket** – Allows bursts, enforces average request rate
* **Trie** – Fast lookup of strings and prefixes
* **Rsync** – Block-level file delta detection and sync

---

## 📘️ Further Reading

If you'd like to go beyond the code and understand the theory behind these algorithms:

👉 [**Key System Design Algorithms Explained Simply**
by @dinesharney on Medium](https://medium.com/@dinesharney/key-system-design-algorithms-explained-simply-8faac0f57422)

---
