# PurelyFunctional Solutions

My solutions to Eric Normand's problems from the _Purely Functional_ newsletter.

## 399 Digit search

[Link](https://gist.github.com/ericnormand/b93b4f9cc9ab0bd5d396c9dac8bcfd7d)

**Digit search**

This is one of those weird programming puzzles with no real point except practice. But it's considered Very Hard in JavaScript. Let's see how we do in Clojure.

Write a function that takes a sequence of integers. You're trying to get all 10 digits by looking through the numbers sequentially. When you have found one instance of every decimal digit, return whatever number you were on when you found the last one. If you get to the end of the sequence without finding all the digits (for instance, maybe there was no `9`), then just return `nil`.

Example

```clojure
(digit-search   [5175 4538 2926 5057 6401 4376 2280 6137]) ;=> 5057
;; digits found: 517- 4-38 29-6 -0

(digit-search   [5719 7218 3989 8161 2676 3847 6896 3370]) ;=> 3370
;; digits found: 5719 -2-8 3--- --6- ---- --4- ---- ---0

(digit-search   [4883 3876 7769 9846 9546 9634 9696 2832]) ;=> nil
;; digits found: 48-3 --76 ---9 ---- -5-- ---- ---- 2---
;; 0 and 1 are missing
```

Thanks to [this site](https://edabit.com/challenge/5hsyLC2Ntgoqn2wAy) for the challenge idea where it is considered Very Hard level in JavaScript.

## My solution 

Submitted 2020-10-30

```clojure
(defn digitize [n]
  (map #(- (int %) 48)
       (seq (str n))))

(defn digit-search [digits]
  (loop [coll digits
         accumulator #{}]
    (if (empty? coll)
      nil
      (if
       (clojure.set/subset? #{0 1 2 3 4 5 6 7 8 9}
                            (clojure.set/union (set (digitize (first coll)))
                                               accumulator))
        (first coll)
        (recur (rest coll)
               (clojure.set/union
                (set (digitize (first coll)))
```

## 400 RGB Color Mixing

[Link](https://gist.github.com/ericnormand/24704c0e4804580c991b68aab29b1c30)

## Description

**RGB Color Mixing**

Here's an algorithm for mixing multiple RGB colors to create a single color.

1. Separate the colors into Red, Green, and Blue components.
2. Average all the Reds together, average all the Greens together, average all the Blues together.
3. Put the average values back together into a resulting RGB.

Is this the right way to do it? I don't know! But it's the way we're going to implement in this problem. Your task is to take a collection of RGB strings of the form `"#FF021A"`, mix them like the algorithm above, and return the resulting RGB string.

Note that each RGB string contains two hexadecimal digits for each component. You can round the averages to integers however you want. 

Examples

```clojure
(mix ["#FFFFFF" "#000000"]) ;=> "#7F7F7F" or "#808080" depending on how you round
(mix ["#FF0000" "#00FF00" "#0000FF"]) ;=> "#555555"
```

Thanks to [this site](https://edabit.com/challenge/aBYTxSRpLpBnMSX3E) for the challenge idea where it is considered Very Hard level in JavaScript.


## My Solution
Submitted on 2020-10-28

```clojure
(defn rgbs [rgb]
  (let [a (subs rgb 1 3)
        b (subs rgb 3 5)
        c (subs rgb 5)]
    (map #(read-string (str "0x" %)) [a b c])))

(defn mix[colors]
  (let [[& color] (map rgbs colors)]
    (str "#" (clojure.string/join ""
                                  (map #(format "%X" %)
                                       (map byte (map #(/ % (count colors))
                                                      (apply map + color))))))))
```

## 401 Sums of Pairs

[Link](https://gist.github.com/ericnormand/c2c94f698bf3ace64c5f722da6dec2fc)

## Description

**Sums of pairs**

Write a function that takes a collection of numbers and a target number. Return all pairs of numbers found in the collection that sum up to the target number.

Examples

```clojure
(sums-of-pairs [2 4 5 6] 8) ;=> [[2 6]]
(sums-of-pairs [3 2 0 1 1 5] 5) ;=> [[2 3] [0 5]]
(sums-of-pairs [1 3 2 3 4 5] 7) ;=> [[3 4] [3 4] [2 5]]
```

Notes

* There can be duplicate numbers.
* Each pair should be sorted.

Thanks to [this site](https://edabit.com/challenge/ZkWSacTDQ65A3gh6j) for the challenge idea where it is considered Expert level in JavaScript.

## My Solution

Submitted 2020-10-26

```clojure
;; create a (count coll) x (count coll) matrix with all possible pair sums from collection
;; restrict upper right matrix, this will omit commutative pair sums
;; omit diagonal to omit reflexive sums.
;; filter only pairs that sum to target and sort
(defn sum-of-pairs [coll target]
  (for [x (map-indexed vector coll)
        y (map-indexed vector coll)
        :when (and (< (first x) (first y)) 
                   (= (+ (second x) (second y)) target))] 
   (vec (sort [(second x) (second y)]))))
```
## 402 Most Frequent
[Link](https://gist.github.com/ericnormand/7944c8806ba447a7bee6301a168ecdcb)

## Description


**Most frequent element**

Write a function that takes a collection and returns the most frequent element. But here's the thing: you can't use the built-in function `clojure.core/frequencies`. And if there are ties, just pick one.

Examples

```clojure
(most-frequent [2 2 3 4 4 2 1 1 3 2]) ;=> 2
(most-frequent []) ;=> nil
(most-frequent [1 1 4 4 5]) ;=> 4
```

Notes

* return `nil` for an empty collection
* in the case of a tie, return one of the winners

Thanks to [this site](https://edabit.com/challenge/hxHBsYebaBM3ff5s6) for the challenge idea where it is considered Very Hard level in JavaScript.

Please submit your solutions as comments on this gist.

## My Solution
Submitted 2020-11-03

```clojure
(defn most-frequent [elements]
  (if (= [] elements) nil
    (loop [el elements
           rsum (apply merge (map #(hash-map (keyword (str %)) 0) (set elements)))]
      (if (empty? el)
        (symbol (key (apply max-key val rsum)))

        (recur (rest el)
               (update-in rsum
                          [(keyword (str (first el)))]
                          inc))))))
```
