# 2-d Orthogonal Line Intersection

## Problem
Given a set of horizontal & vertical lines, find:
* All points where 2 lines intersect

## Algorithm

### Brute Force

N^2 search of every horizontal line with every vertical line

### Sweep Line

* Sweep from left to right, acting upon the following events:
  * Start of a horizontal line - enter the y coord into a BST
  * End of the horizontal line - remove the y coord from the BST
  * Vertical line - do a range search against all y coordinates left in the BST
    
## Examples

```
% java -cp target/2d-line-intersection-1.0-SNAPSHOT-jar-with-dependencies.jar org.sheldon.lineintersection.IntersectionComparison 10000
Brute: Intersections: 11067255 {took: 1386}
Sweep: Intersections: 11067255 {took: 576}
```

```
% java -jar target/2d-line-intersection-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/horizontal10.txt
```
