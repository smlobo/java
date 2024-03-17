# Point Location

https://introcs.cs.princeton.edu/java/assignments/locate.html

## Problem
Given a set of lines, and a set of point pairs, determine if any line 
splits that pair of points.

## Algorithm

### Brute
* Iterate over every line and point pair - M*N

### Binary Tree
* Create a binary tree where:
  * Internal nodes are line segments
  * External nodes are regions these line segments divide the plane into
* For each point in a pair, determine which region they are in.
* If the region is the same, the point pair is not split by any line
* At worst M*M regions. Binary tree lookup will be log(M^2) = 2logM
* Total search is 2logM * N

## Examples

* `input100-99998no.txt`
  ```
  Reading in 100 lines
  Brute: 0 separable of 99998; took: 73
  Binary tree: 0 separable of 99998; took: 36
  ```
* `input1000-100000no.txt`
  ```
  Reading in 1000 lines
  Brute: 0 separable of 100000; took: 1082
  Binary tree: 0 separable of 100000; took: 208
  ```
* `input1000-100000yes.txt`
  ```
  Reading in 1000 lines
  Brute: 100000 separable of 100000; took: 20
  Binary tree: 100000 separable of 100000; took: 166
  ```
