# Smallest Enclosing Circle

https://introcs.cs.princeton.edu/java/assignments/circle.html

## Problem
Given a set of points on a 2d plane, find the smallest circle that 
encloses all points.

## Algorithm

### Brute
* Pair at the diameter:
  * Iterate over all pairs - N^2
* Triple on the circumference:
  * Iterate over all triples - N^3

### Fast
* Pair at the diameter:
  * Build a KdTree - N.logN
  * Iterate over all finding the furthest pair - N.logN
* Triple on the circumference:
  * Locate the leftmost point - N
  * Sort by slope to this point - N.logN
  * Graham scan to find points on the convex hull - N
  * Iterate over convex hull triples - M^3 (M<<N)

## Examples

* `input1000.txt`
  ```
  Brute SEC Pair: (<0.000,0.000>, 1.414) [took: 31]
                  null <-> null
  Brute SEC Triple: (<0.257,0.012>, 0.884) [took: 8687]
                    <0.505,-0.836> ; <0.393,-0.861> ; <0.053,0.872>
  Brute SEC: (<0.257,0.012>, 0.884)
  Brute solution: (<0.257,0.012>, 0.884) [took: 8754]
  Fast SEC Pair: (<0.000,0.000>, 1.414) [took: 11]
                 null <-> null
  Fast SEC Triple: (<0.257,0.012>, 0.884) [took: 14]
                   <0.053,0.872> ; <0.505,-0.836> ; <0.393,-0.861>
  Fast SEC: (<0.257,0.012>, 0.884)
  Fast solution: (<0.257,0.012>, 0.884) [took: 30]
  ```
* `antipodal1000.txt`
  ```
  Brute SEC Pair: (<-0.076,0.099>, 0.894) [took: 23]
                  <0.800,0.280> <-> <-0.951,-0.083>
  Brute SEC Triple: (<-0.065,0.049>, 0.895) [took: 7878]
                    <0.800,0.280> ; <-0.951,-0.083> ; <-0.203,0.934>
  Brute SEC: (<-0.076,0.099>, 0.894)
  Brute solution: (<-0.076,0.099>, 0.894) [took: 7941]
  Fast SEC Pair: (<-0.076,0.099>, 0.894) [took: 8]
                 <0.800,0.280> <-> <-0.951,-0.083>
  Fast SEC Triple: (<-0.065,0.049>, 0.895) [took: 14]
                   <-0.951,-0.083> ; <-0.203,0.934> ; <0.800,0.280>
  Fast SEC: (<-0.076,0.099>, 0.894)
  Fast solution: (<-0.076,0.099>, 0.894) [took: 27]
  ```