# Secret-Sharing-Solver
# Secret Sharing - Polynomial Constant Term Extraction

This project is a simplified implementation of **Shamir's Secret Sharing** algorithm that recovers the **constant term** (`c`) of an unknown polynomial using Lagrange Interpolation.

---

## Problem Overview

You are given multiple encoded points of a polynomial in JSON format. Each point is represented with:
- A **key** as `x`
- A **value** encoded in a given base as `y`

Using `k` such points (where `k = degree + 1`), recover the **constant term** `c = f(0)` of the polynomial.

---

## Features

- Reads **multiple test cases** from an input JSON file
- Decodes y-values using **base conversions**
- Applies **Lagrange Interpolation** to find `f(0)`
- Prints secret for **each test case**

---

