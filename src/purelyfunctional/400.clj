(ns purelyfunctional.400)

(defn rgbs [rgb]
  (let [a (subs rgb 1 3)
        b (subs rgb 3 5)
        c (subs rgb 5)]
    (map #(read-string (str "0x" %)) [a b c])))

(defn mix[colors]
  (let [[& color] (map rgbs colors)]
    (str "#" (clojure.string/join ""
                                  (map #(format "%X" %)
                                       (map byte (map #(/ % (count colors)))))))))
  

