# Assignment 5 – COSC 4P03 / 4P04  
**Complete Written Answers**

---

## Question 1 – Subset-Sum

### (a) What is a pseudopolynomial algorithm?

A **pseudopolynomial algorithm** is one whose running time is polynomial in the *numeric value* of the input (e.g., the target sum T), but exponential in the *size* of the input measured in bits. Because a number T can be encoded in only ⌈log₂T⌉ bits, an algorithm that runs in time O(T) is actually exponential in the input bit-length — it just does not look that way when T is written as a decimal integer. Pseudopolynomial algorithms are efficient in practice when the numbers involved are small, but they are **not** truly polynomial-time algorithms.

---

### (b) Running time of the DP Subset-Sum algorithm, and why it is pseudopolynomial

**Running time:** O(n · T)

The standard DP constructs a boolean table `dp[0..n][0..T]` where `dp[i][j] = TRUE` iff some subset of the first *i* elements sums to *j*. Filling each cell takes O(1), there are (n+1)(T+1) cells, giving **O(nT)** total time.

**Why pseudopolynomial:** The input size in bits is O(n log M + log T), where M is the largest element value. The value T itself requires only O(log T) bits to represent. The running time O(nT) = O(n · 2^(log T)), which is **exponential** in the bit-length of T. Hence the algorithm is polynomial in the *numeric value* of T but not in the *bit-length* of the input — the definition of pseudopolynomial.

---

### (c) DP Table for X = {4, 5, 6, 2, 7, 8, 9}, T = 10

**Recurrence:** `dp[i][j] = dp[i-1][j]  OR  (j ≥ X[i]  AND  dp[i-1][j − X[i]])`  
**Base case:** `dp[0][0] = T`, `dp[0][j] = F` for j > 0

|        | j=0 | j=1 | j=2 | j=3 | j=4 | j=5 | j=6 | j=7 | j=8 | j=9 | j=10 |
|--------|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|------|
| i=0    |  T  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F  |  F   |
| i=1 (4)|  T  |  F  |  F  |  F  |  T  |  F  |  F  |  F  |  F  |  F  |  F   |
| i=2 (5)|  T  |  F  |  F  |  F  |  T  |  T  |  F  |  F  |  F  |  T  |  F   |
| i=3 (6)|  T  |  F  |  F  |  F  |  T  |  T  |  T  |  F  |  F  |  T  | **T**|
| i=4 (2)|  T  |  F  |  T  |  F  |  T  |  T  |  T  |  T  |  T  |  T  |  T   |
| i=5 (7)|  T  |  F  |  T  |  F  |  T  |  T  |  T  |  T  |  T  |  T  |  T   |
| i=6 (8)|  T  |  F  |  T  |  F  |  T  |  T  |  T  |  T  |  T  |  T  |  T   |
| i=7 (9)|  T  |  F  |  T  |  F  |  T  |  T  |  T  |  T  |  T  |  T  |  T   |

**Answer:** `dp[7][10] = TRUE`. Yes, there is a subset of X with sum T = 10.  
The first witness appears at row i=3: subset **{4, 6}** (4 + 6 = 10).

---

## Question 2 – CircuitSatisfiability to SAT

**Circuit structure (from Figure 1):**

| Gate | Type | Inputs     | Variable |
|------|------|------------|----------|
| g₁   | AND  | x₁, x₂    | g₁ = x₁ ∧ x₂ |
| g₂   | NOT  | x₄         | g₂ = ¬x₄ |
| g₃   | OR   | x₃, x₂    | g₃ = x₃ ∨ x₂ |
| g₄   | OR   | g₁, g₂    | g₄ = g₁ ∨ g₂ = (x₁∧x₂) ∨ ¬x₄ |
| g₅   | AND  | g₂, g₃    | g₅ = g₂ ∧ g₃ = ¬x₄ ∧ (x₃∨x₂) |
| y    | NOR  | g₄, g₅    | y = ¬(g₄ ∨ g₅) |

**Simplified output:** Since g₅ → ¬x₄ → g₄, we have g₄ ∨ g₅ = g₄, so:  
**y = ¬g₄ = ¬((x₁∧x₂) ∨ ¬x₄) = x₄ ∧ (¬x₁ ∨ ¬x₂)**

---

### (a) Number of input combinations

With 4 binary inputs (x₁, x₂, x₃, x₄): **2⁴ = 16 combinations**.

---

### (b) Full truth table

| x₁ | x₂ | x₃ | x₄ | g₁ | g₂ | g₃ | g₄ | g₅ | **y** |
|----|----|----|-----|----|----|----|----|----|----|
|  0 |  0 |  0 |  0 |  0 |  1 |  0 |  1 |  0 | **0** |
|  0 |  0 |  0 |  1 |  0 |  0 |  0 |  0 |  0 | **1** |
|  0 |  0 |  1 |  0 |  0 |  1 |  1 |  1 |  1 | **0** |
|  0 |  0 |  1 |  1 |  0 |  0 |  1 |  0 |  0 | **1** |
|  0 |  1 |  0 |  0 |  0 |  1 |  1 |  1 |  1 | **0** |
|  0 |  1 |  0 |  1 |  0 |  0 |  1 |  0 |  0 | **1** |
|  0 |  1 |  1 |  0 |  0 |  1 |  1 |  1 |  1 | **0** |
|  0 |  1 |  1 |  1 |  0 |  0 |  1 |  0 |  0 | **1** |
|  1 |  0 |  0 |  0 |  0 |  1 |  0 |  1 |  0 | **0** |
|  1 |  0 |  0 |  1 |  0 |  0 |  0 |  0 |  0 | **1** |
|  1 |  0 |  1 |  0 |  0 |  1 |  1 |  1 |  1 | **0** |
|  1 |  0 |  1 |  1 |  0 |  0 |  1 |  0 |  0 | **1** |
|  1 |  1 |  0 |  0 |  1 |  1 |  1 |  1 |  1 | **0** |
|  1 |  1 |  0 |  1 |  1 |  0 |  1 |  1 |  0 | **0** |
|  1 |  1 |  1 |  0 |  1 |  1 |  1 |  1 |  1 | **0** |
|  1 |  1 |  1 |  1 |  1 |  0 |  1 |  1 |  0 | **0** |

**y = TRUE** for exactly **6** input combinations (all have x₄=1 and (x₁,x₂) ≠ (1,1)).

---

### (c) Reduction to SAT

Introduce a Boolean variable for each gate output: g₁, g₂, g₃, g₄, g₅, y.  
Encode each gate as CNF clauses (Tseitin transformation), then assert y = TRUE.

**AND gate: g₁ = x₁ ∧ x₂**  
(¬g₁ ∨ x₁) ∧ (¬g₁ ∨ x₂) ∧ (¬x₁ ∨ ¬x₂ ∨ g₁)

**NOT gate: g₂ = ¬x₄**  
(¬g₂ ∨ ¬x₄) ∧ (g₂ ∨ x₄)

**OR gate: g₃ = x₃ ∨ x₂**  
(¬g₃ ∨ x₃ ∨ x₂) ∧ (¬x₃ ∨ g₃) ∧ (¬x₂ ∨ g₃)

**OR gate: g₄ = g₁ ∨ g₂**  
(¬g₄ ∨ g₁ ∨ g₂) ∧ (¬g₁ ∨ g₄) ∧ (¬g₂ ∨ g₄)

**AND gate: g₅ = g₂ ∧ g₃**  
(¬g₅ ∨ g₂) ∧ (¬g₅ ∨ g₃) ∧ (¬g₂ ∨ ¬g₃ ∨ g₅)

**NOR gate: y = ¬(g₄ ∨ g₅)**  
(¬y ∨ ¬g₄) ∧ (¬y ∨ ¬g₅) ∧ (g₄ ∨ g₅ ∨ y)

**Assert output TRUE:** (y)

**Full SAT instance** (18 clauses):  
```
(¬g₁∨x₁) ∧ (¬g₁∨x₂) ∧ (¬x₁∨¬x₂∨g₁)  
∧ (¬g₂∨¬x₄) ∧ (g₂∨x₄)  
∧ (¬g₃∨x₃∨x₂) ∧ (¬x₃∨g₃) ∧ (¬x₂∨g₃)  
∧ (¬g₄∨g₁∨g₂) ∧ (¬g₁∨g₄) ∧ (¬g₂∨g₄)  
∧ (¬g₅∨g₂) ∧ (¬g₅∨g₃) ∧ (¬g₂∨¬g₃∨g₅)  
∧ (¬y∨¬g₄) ∧ (¬y∨¬g₅) ∧ (g₄∨g₅∨y)  
∧ (y)
```

---

### (d) Verification – SAT is satisfiable for each circuit-satisfying assignment

All 6 satisfying inputs share the same pattern: **x₄ = 1**, so g₂ = 0; and **(x₁,x₂) ≠ (1,1)**, so g₁ = 0. This gives g₄ = 0, g₅ = 0, y = 1. The value of x₃ is irrelevant to y but affects g₃.

**Representative case: (x₁=0, x₂=0, x₃=0, x₄=1)**  
g₁=0, g₂=0, g₃=0, g₄=0, g₅=0, y=1

Checking all 18 clauses:

| Clause | Value | Satisfied? |
|--------|-------|-----------|
| (¬g₁∨x₁) = (1∨0) | 1 | ✓ |
| (¬g₁∨x₂) = (1∨0) | 1 | ✓ |
| (¬x₁∨¬x₂∨g₁) = (1∨1∨0) | 1 | ✓ |
| (¬g₂∨¬x₄) = (1∨0) | 1 | ✓ |
| (g₂∨x₄) = (0∨1) | 1 | ✓ |
| (¬g₃∨x₃∨x₂) = (1∨0∨0) | 1 | ✓ |
| (¬x₃∨g₃) = (1∨0) | 1 | ✓ |
| (¬x₂∨g₃) = (1∨0) | 1 | ✓ |
| (¬g₄∨g₁∨g₂) = (1∨0∨0) | 1 | ✓ |
| (¬g₁∨g₄) = (1∨0) | 1 | ✓ |
| (¬g₂∨g₄) = (1∨0) | 1 | ✓ |
| (¬g₅∨g₂) = (1∨0) | 1 | ✓ |
| (¬g₅∨g₃) = (1∨0) | 1 | ✓ |
| (¬g₂∨¬g₃∨g₅) = (1∨1∨0) | 1 | ✓ |
| (¬y∨¬g₄) = (0∨1) | 1 | ✓ |
| (¬y∨¬g₅) = (0∨1) | 1 | ✓ |
| (g₄∨g₅∨y) = (0∨0∨1) | 1 | ✓ |
| (y) = (1) | 1 | ✓ |

All 18 clauses satisfied. ✓

For all other 5 satisfying inputs, the intermediate variables are identical (same pattern: g₁=0, g₂=0, g₄=0, g₅=0, y=1; only g₃ may change to 1 when x₂=1 or x₃=1). In those cases every clause remains satisfied by the same argument (¬g₁, ¬g₂, ¬g₄, ¬g₅ all evaluate to 1, satisfying any clause they appear positively in; y=1 satisfies (y) and all clauses involving y negated are satisfied since g₄=g₅=0).

**Conclusion:** For every circuit-satisfying input, assigning the corresponding intermediate variable values satisfies the SAT instance — confirming the correctness of the reduction.

---

## Question 3 – 3-SAT to MaxIndSet

**Formula:** Φ = (a ∨ a ∨ a) ∧ (ā ∨ ā ∨ ā) ∧ (b ∨ d ∨ c̄)  
(Variables: a, b, c, d)

---

### (a) Reduction to MaxIndSet; value of k

**k = 3** (one per clause — k equals the number of clauses in 3-SAT).

**Construction:** Create one node per literal occurrence (9 nodes total):

| Node | Clause | Literal |
|------|--------|---------|
| 1    | C₁     | a  |
| 2    | C₁     | a  |
| 3    | C₁     | a  |
| 4    | C₂     | ā  |
| 5    | C₂     | ā  |
| 6    | C₂     | ā  |
| 7    | C₃     | b  |
| 8    | C₃     | d  |
| 9    | C₃     | c̄ |

**Edges added:**

1. **Within each clause** (conflict — can't pick two from same clause):  
   (1,2), (1,3), (2,3) — clause C₁  
   (4,5), (4,6), (5,6) — clause C₂  
   (7,8), (7,9), (8,9) — clause C₃

2. **Between complementary literals across clauses** (a ↔ ā):  
   (1,4), (1,5), (1,6), (2,4), (2,5), (2,6), (3,4), (3,5), (3,6)  
   *(b, d, c̄ have no complementary literals in other clauses — no additional cross-clause edges)*

**Graph:**  
- Nodes: {1, 2, 3, 4, 5, 6, 7, 8, 9}  
- Edges: the 9 within-clause edges + 9 cross-clause (complementary) edges = **18 edges total**

**k = 3**

---

### (b) No independent set of size k = 3

To form an IS of size 3, we must pick exactly one node from each clause (since nodes within the same clause are all mutually connected).

- Pick any node from C₁ → it represents literal **a**.  
- Pick any node from C₂ → it represents literal **ā**.  
- Nodes representing **a** (C₁) and nodes representing **ā** (C₂) are **complementary** — every such pair has an edge between them (added in step 2 above).

Therefore, any candidate triple (one from each clause) always includes an edge between the C₁-node and the C₂-node. **No independent set of size 3 exists.**

(Note: an IS of size 2 does exist, e.g., {1, 7} — pick node 1 from C₁ and node 7 from C₃; these are not connected. But size 3 is impossible.)

---

### (c) SAT instance is not satisfiable — exhaustive check

Φ = **(a)** ∧ **(ā)** ∧ (b ∨ d ∨ c̄)

Clauses C₁ and C₂ alone are already contradictory:
- C₁ = a: requires a = TRUE  
- C₂ = ā: requires a = FALSE  

Full table (2⁴ = 16 rows; only 'a' matters for satisfiability):

| a | b | c | d | C₁=(a∨a∨a) | C₂=(ā∨ā∨ā) | C₃=(b∨d∨c̄) | **Φ** |
|---|---|---|---|:-----------:|:-----------:|:-----------:|:-----:|
| 0 | 0 | 0 | 0 | F | T | T | **F** |
| 0 | 0 | 0 | 1 | F | T | T | **F** |
| 0 | 0 | 1 | 0 | F | T | F | **F** |
| 0 | 0 | 1 | 1 | F | T | T | **F** |
| 0 | 1 | 0 | 0 | F | T | T | **F** |
| 0 | 1 | 0 | 1 | F | T | T | **F** |
| 0 | 1 | 1 | 0 | F | T | T | **F** |
| 0 | 1 | 1 | 1 | F | T | T | **F** |
| 1 | 0 | 0 | 0 | T | F | T | **F** |
| 1 | 0 | 0 | 1 | T | F | T | **F** |
| 1 | 0 | 1 | 0 | T | F | F | **F** |
| 1 | 0 | 1 | 1 | T | F | T | **F** |
| 1 | 1 | 0 | 0 | T | F | T | **F** |
| 1 | 1 | 0 | 1 | T | F | T | **F** |
| 1 | 1 | 1 | 0 | T | F | T | **F** |
| 1 | 1 | 1 | 1 | T | F | T | **F** |

**All 16 combinations yield Φ = FALSE.** The SAT instance is **UNSATISFIABLE**.

This is consistent with the MaxIndSet result: no IS of size k=3 ↔ the 3-SAT formula is not satisfiable.

---

## Question 4 – Research

### (a) An NP-complete problem (A) and its NP-hardness proof

**Problem A: Vertex Cover (VC)**

*Definition:* Given a graph G = (V, E) and integer k, is there a set S ⊆ V with |S| ≤ k such that every edge has at least one endpoint in S?

*NP-hardness source:* Vertex Cover was proved NP-hard by reduction from **Independent Set (IS)**.  
*Reduction:* A set S ⊆ V is an independent set of size k in G if and only if V \ S is a vertex cover of size n − k. Thus a polynomial-time algorithm for VC would solve IS in polynomial time, and IS is NP-hard (via 3-SAT → IS reduction). Since VC is also trivially in NP (guess S and verify in polynomial time), VC is **NP-complete**.

*Reference:* Karp, R. M. (1972). "Reducibility Among Combinatorial Problems." In *Complexity of Computer Computations*, pp. 85–103.

---

### (b) An NP-hard problem (B) that is NOT NP-complete

**Problem B: Halting Problem**

*Definition:* Given a Turing machine M and input w, does M halt on w?

*NP-hardness source:* The Halting Problem was proved NP-hard (in fact, harder than any NP problem) because every NP problem can be reduced to it via a polynomial-time many-one reduction. In particular, **SAT** (which is NP-complete by the Cook–Levin theorem) reduces to the Halting Problem: given a SAT formula φ, construct a TM that enumerates all variable assignments and halts iff it finds a satisfying one.

*Why NOT NP-complete:* A problem is NP-complete only if it is *in NP* (i.e., solvable in nondeterministic polynomial time with a polynomial-length certificate). The Halting Problem is **undecidable** — no algorithm of any time complexity can solve it — so it is not in NP, and therefore **not NP-complete**.

*Reference:* Turing, A. M. (1936). "On Computable Numbers, with an Application to the Entscheidungsproblem." *Proceedings of the London Mathematical Society*, 42(1), 230–265.

---

## Question 6 – FastSubsetSum Implementation (Java)

See `FastSubsetSum.java` in this submission.

**Algorithm:** Implements the `FastSubsetSum` algorithm from Erickson's *Algorithms* (2019).  
Maintains a set S of all achievable partial sums ≤ T. For each element x, extends S by adding x to each existing sum, keeping only values ≤ T. Returns TRUE iff T ∈ S after processing all elements.

**Verified outputs:**
- `subsetsum1.txt` (X={8,6,7,5,3,10,9}, T=15): **TRUE** (e.g., 8+7=15)
- `subsetsum2.txt` (X={11,6,5,1,7,12,13}, T=15): **FALSE**
